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
import com.example.appsfactory.domain.repository.MainRepository
import com.example.appsfactory.util.ApiState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : MainRepository {

    override fun getArtist(artistName: String) = flow {
        emit(ApiState.Loading)

        val artist = apiService.getArtist(artistName).results.artistmatches.artist
        emit(ApiState.Success(artist))
    }
        .catch { e -> emit(ApiState.Error(e.message.toString())) }
        .flowOn(ioDispatcher)

    override fun getTopAlbumsBasedOnArtist(artistName: String) = flow {
        val response = apiService.getTopAlbumsBasedOnArtist(artistName).topalbums.album
        emit(response)
    }.flowOn(ioDispatcher)
}