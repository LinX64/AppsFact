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
import com.example.appsfactory.di.modules.IoDispatcher
import com.example.appsfactory.domain.repository.AlbumInfoRepository
import com.example.appsfactory.util.ApiState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import okio.IOException

class AlbumInfoRepositoryImpl(
    private val apiService: ApiService,
    private val appDb: AppDatabase,
    private val isNetworkAvailable: Boolean,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : AlbumInfoRepository {

    override suspend fun getAlbumInfo(
        albumName: String,
        artistName: String
    ): Flow<ApiState<AlbumInfoEntity>> = flow {
        emit(ApiState.Loading(true))

        if (isNetworkAvailable) {
            val album = apiService.getAlbumInfo(albumName, artistName).album
            val albumEntity = AlbumInfoEntity(
                album.playcount.toInt(),
                album.name,
                album.artist,
                album.image[2].text,
                album.tracks.track.toString(),
                album.wiki.summary
            )

            appDb.albumInfoDao().insert(albumEntity)
            emit(ApiState.Success(albumEntity))
        }
        // retrieve data from db if available
        appDb.albumInfoDao().getAlbumInfo(albumName, artistName)
            .filterNotNull()
            .collect { emit(ApiState.Success(it)) }
    }
        .catch { e ->
            if (e is IOException) emit(ApiState.Error("No Internet Connection")) else emit(
                ApiState.Error(e.message.toString())
            )
        }
        .flowOn(ioDispatcher)

    override suspend fun delete() = appDb.albumInfoDao().deleteAll()
}