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
import com.example.appsfactory.domain.model.artistList.Artist
import com.example.appsfactory.domain.model.top_albums.TopAlbum
import com.example.appsfactory.domain.repository.MainRepository
import com.example.appsfactory.util.ApiState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okio.IOException

class MainRepositoryImpl(
    private val apiService: ApiService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : MainRepository {

    override fun getArtist(artistName: String): Flow<ApiState<List<Artist>>> = flow {
        emit(ApiState.Loading())

        val artist = apiService.getArtist(artistName).results.artistmatches.artist
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
            .catch { e ->
                if (e is IOException) emit(ApiState.Error("No Internet Connection")) else emit(
                    ApiState.Error(e.message.toString())
                )
            }.flowOn(ioDispatcher)
}