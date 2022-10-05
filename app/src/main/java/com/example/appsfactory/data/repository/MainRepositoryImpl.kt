/*
 * *
 *  * Created by Mohsen on 10/4/22, 1:43 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 10/4/22, 1:43 PM
 *
 */

package com.example.appsfactory.data.repository

import com.example.appsfactory.data.source.remote.ApiService
import com.example.appsfactory.di.modules.IoDispatcher
import com.example.appsfactory.domain.model.albumInfo.Album
import com.example.appsfactory.domain.model.artistList.Artistmatches
import com.example.appsfactory.domain.model.top_albums.TopAlbum
import com.example.appsfactory.domain.repository.MainRepository
import com.example.appsfactory.util.ApiState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : MainRepository {

    override suspend fun getArtist(artistName: String): Flow<ApiState<Artistmatches>> = flow {
        emit(ApiState.Loading(true))

        val response = apiService.getArtist(artistName).results.artistmatches
        emit(ApiState.Success(response))
    }
        .catch { e -> emit(ApiState.Error(e.message.toString())) }
        .flowOn(ioDispatcher)

    override suspend fun getTopAlbumsBasedOnArtist(artistName: String): Flow<ApiState<List<TopAlbum>>> =
        flow {
            emit(ApiState.Loading(true))

            val response = apiService.getTopAlbumsBasedOnArtist(artistName).topalbums.album
            emit(ApiState.Success(response))
        }
            .catch { e -> emit(ApiState.Error(e.message.toString())) }
            .flowOn(ioDispatcher)

    override suspend fun getAlbumInfo(
        albumName: String,
        artistName: String
    ): Flow<ApiState<Album>> = flow {
        emit(ApiState.Loading(true))

        val response = apiService.getAlbumInfo(albumName, artistName).album
        emit(ApiState.Success(response))
    }
        .catch { e -> emit(ApiState.Error(e.message.toString())) }
        .flowOn(ioDispatcher)
}