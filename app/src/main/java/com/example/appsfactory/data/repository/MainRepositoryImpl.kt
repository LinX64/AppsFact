/*
 * *
 *  * Created by Mohsen on 10/4/22, 1:43 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 10/4/22, 1:43 PM
 *
 */

package com.example.appsfactory.data.repository

import com.example.appsfactory.data.source.local.AppDatabase
import com.example.appsfactory.data.source.local.entity.LocalAlbum
import com.example.appsfactory.data.source.remote.ApiService
import com.example.appsfactory.domain.model.albumInfo.Album
import com.example.appsfactory.domain.model.artistList.Artistmatches
import com.example.appsfactory.domain.model.top_albums.TopAlbum
import com.example.appsfactory.domain.repository.MainRepository
import com.example.appsfactory.util.ApiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class MainRepositoryImpl(
    private val apiService: ApiService,
    private val appDb: AppDatabase
) : MainRepository {

    override suspend fun getArtist(artistName: String): Flow<ApiState<Artistmatches>> = flow {
        emit(ApiState.Loading(true))

        val response = apiService.getArtist(artistName).results.artistmatches
        emit(ApiState.Success(response))
    }
        .catch { e -> emit(ApiState.Error(e.message.toString())) }
        .flowOn(Dispatchers.IO)

    override suspend fun getTopAlbumsBasedOnArtist(artistName: String): Flow<ApiState<List<TopAlbum>>> =
        flow {
            emit(ApiState.Loading(true))

            val response = apiService.getTopAlbumsBasedOnArtist(artistName).topalbums.album
            emit(ApiState.Success(response))
        }
            .onEach {
                if (it is ApiState.Success) {
                    val albums = it.data.map { album ->
                        LocalAlbum(
                            album.playcount,
                            album.name,
                            album.artist.name,
                            album.image[0].text
                        )
                    }
                    appDb.topAlbumDao().insertAll(albums)
                }
            }
            .catch { e -> emit(ApiState.Error(e.message.toString())) }
            .flowOn(Dispatchers.IO)

    override suspend fun getAlbumInfo(
        albumName: String,
        artistName: String
    ): Flow<ApiState<Album>> = flow {
        emit(ApiState.Loading(true))

        val response = apiService.getAlbumInfo(albumName, artistName).album
        emit(ApiState.Success(response))
    }
        .catch { e -> emit(ApiState.Error(e.message.toString())) }
        .flowOn(Dispatchers.IO)
}