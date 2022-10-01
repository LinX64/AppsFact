package com.example.appsfactory.data.remote.repository

import com.example.appsfactory.data.remote.api.ApiService
import com.example.appsfactory.util.NetworkResult
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getArtist(artist: String) = flow {
        emit(NetworkResult.Loading(true))

        val response = apiService.artistSearch(artist).results.artistmatches
        emit(NetworkResult.Success(response))
    }.catch { e -> emit(NetworkResult.Error(e.message.toString())) }

    suspend fun getTopAlbumsBasedOnArtist(artist: String) = flow {
        emit(NetworkResult.Loading(true))

        val response = apiService.getArtistTopAlbums(artist).topalbums.album
        emit(NetworkResult.Success(response))
    }.catch { e -> emit(NetworkResult.Error(e.message.toString())) }
}