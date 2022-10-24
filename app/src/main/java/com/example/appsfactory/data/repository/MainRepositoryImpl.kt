/*
 * *
 *  * Created by Mohsen on 10/4/22, 1:43 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 10/4/22, 1:43 PM
 *
 */

package com.example.appsfactory.data.repository

import com.example.appsfactory.data.source.local.AppDatabase
import com.example.appsfactory.data.source.remote.ApiService
import com.example.appsfactory.di.modules.IoDispatcher
import com.example.appsfactory.domain.model.artistList.Artistmatches
import com.example.appsfactory.domain.model.top_albums.TopAlbum
import com.example.appsfactory.domain.model.top_albums.toEntity
import com.example.appsfactory.domain.repository.MainRepository
import com.example.appsfactory.util.ApiState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import okio.IOException

class MainRepositoryImpl(
    private val apiService: ApiService,
    private val appDb: AppDatabase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : MainRepository {

    private val topAlbumsDao = appDb.topAlbumDao()

    override fun getArtist(artistName: String): Flow<ApiState<Artistmatches>> = flow {
        emit(ApiState.Loading())

        val artist = apiService.getArtist(artistName).results.artistmatches
        emit(ApiState.Success(artist))
    }
        .catch { e -> emit(ApiState.Error(e.message.toString())) }
        .flowOn(ioDispatcher)

    override fun getTopAlbumsBasedOnArtist(artistName: String): Flow<ApiState<List<TopAlbum>>> =
        flow {
            emit(ApiState.Loading())

            val response = apiService.getTopAlbumsBasedOnArtist(artistName).topalbums.album
            emit(ApiState.Success(data = response))
        }
            .onEach { saveToDb(it) }
            .catch { e ->
                if (e is IOException) emit(ApiState.Error("No Internet Connection")) else emit(
                    ApiState.Error(e.message.toString())
                )
            }
            .flowOn(ioDispatcher)

    private suspend fun saveToDb(it: ApiState<List<TopAlbum>>) {
        if (it is ApiState.Success) {
            val albums = it.data?.toEntity()
            albums ?: return
            topAlbumsDao.insertAll(albums)

            //TODO: Save only one Item to Db
        }
    }
}