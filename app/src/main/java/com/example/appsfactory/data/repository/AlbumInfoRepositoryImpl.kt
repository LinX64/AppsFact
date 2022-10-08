/*
 * *
 *  * Created by Mohsen on 10/8/22, 3:50 AM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 10/8/22, 3:39 AM
 *
 */

package com.example.appsfactory.data.repository

import com.example.appsfactory.data.source.local.AppDatabase
import com.example.appsfactory.data.source.local.entity.AlbumInfoEntity
import com.example.appsfactory.data.source.remote.ApiService
import com.example.appsfactory.domain.repository.AlbumInfoRepository
import com.example.appsfactory.util.ApiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class AlbumInfoRepositoryImpl(
    private val apiService: ApiService,
    private val appDb: AppDatabase,
    private val isNetworkAvailable: Boolean
) : AlbumInfoRepository {

    override suspend fun getAlbumInfo(
        albumName: String,
        artistName: String
    ): Flow<ApiState<AlbumInfoEntity>> = flow {
        emit(ApiState.Loading(true))

        when (isNetworkAvailable) {
            true -> {
                val album = apiService.getAlbumInfo(albumName, artistName).album
                val albumEntity = AlbumInfoEntity(
                    album.playcount.toInt(),
                    album.name,
                    album.artist,
                    album.image[3].text,
                    album.tracks.track.toString(),
                    album.wiki.summary
                )

                appDb.albumInfoDao().insert(albumEntity)
                emit(ApiState.Success(albumEntity))
            }
            false -> emit(ApiState.Error("No internet connection"))
        }

        // retrieve data from db if available
        val getAlbumInfo = appDb.albumInfoDao().getAlbumInfo(albumName, artistName)
        getAlbumInfo.filterNotNull().collect { albumInfo ->
            emit(ApiState.Success(albumInfo))
        }
    }
        .catch { e -> emit(ApiState.Error(e.message.toString())) }
        .flowOn(Dispatchers.IO)

    override suspend fun delete() = appDb.albumInfoDao().deleteAll()
}