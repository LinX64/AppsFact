/*
 * *
 *  * Created by Mohsen on 10/4/22, 1:43 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 10/4/22, 1:43 PM
 *
 */

package com.example.appsfactory.data.repository

import com.example.appsfactory.data.source.local.AppDatabase
import com.example.appsfactory.data.source.local.entity.AlbumEntity
import com.example.appsfactory.data.source.remote.ApiService
import com.example.appsfactory.di.modules.IoDispatcher
import com.example.appsfactory.domain.model.artistList.Artistmatches
import com.example.appsfactory.domain.model.top_albums.TopAlbum
import com.example.appsfactory.domain.repository.MainRepository
import com.example.appsfactory.util.ApiState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*

class MainRepositoryImpl(
    private val apiService: ApiService,
    private val appDb: AppDatabase,
    private val isNetworkAvailable: Boolean,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : MainRepository {

    override suspend fun getArtist(artistName: String): Flow<ApiState<Artistmatches>> = flow {
        emit(ApiState.Loading(true))

        if (isNetworkAvailable) {
            val artist = apiService.getArtist(artistName).results.artistmatches
            emit(ApiState.Success(artist))
        } else emit(ApiState.Error("No internet connection"))
    }
        .catch { e -> emit(ApiState.Error(e.message.toString())) }
        .flowOn(ioDispatcher)

    override suspend fun getTopAlbumsBasedOnArtist(artistName: String): Flow<ApiState<List<TopAlbum>>> =
        flow {
            emit(ApiState.Loading(true))

            if (isNetworkAvailable) {
                val response = apiService.getTopAlbumsBasedOnArtist(artistName).topalbums.album
                emit(ApiState.Success(response))
            } else emit(ApiState.Error("No internet connection"))
        }
            .onEach {
                if (it is ApiState.Success) {
                    val albums = it.data.map { album ->
                        AlbumEntity(
                            album.playcount,
                            album.name,
                            album.artist.name,
                            album.image[2].text
                        )
                    }
                    appDb.topAlbumDao().insertAll(albums)
                }
            }
            .catch { e -> emit(ApiState.Error(e.message.toString())) }
            .flowOn(ioDispatcher)
}