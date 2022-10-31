/*
 * *
 *  * Created by Mohsen on 10/4/22, 2:05 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 10/4/22, 2:05 PM
 *
 */

package com.example.appsfactory.domain.usecase

import com.example.appsfactory.domain.model.top_albums.TopAlbum
import com.example.appsfactory.domain.repository.AlbumRepository
import com.example.appsfactory.domain.repository.MainRepository
import com.example.appsfactory.util.ApiState
import com.example.appsfactory.util.ApiState.Success
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GetTopAlbumsUseCase @Inject constructor(
    private val mainRepository: MainRepository,
    private val localAlbumRepository: AlbumRepository
) {

    operator fun invoke(artistName: String): Flow<ApiState<List<TopAlbum>>> {
        val bookmarkedAlbums = localAlbumRepository.getBookmarkedAlbums()
        val remoteTopAlbums = mainRepository.getTopAlbumsBasedOnArtist(artistName)

        return combine(remoteTopAlbums, bookmarkedAlbums) { topAlbums, bookmarked ->
            topAlbums.map { topAlbum ->
                val isBookmarked =
                    bookmarked.find { it.name == topAlbum.name }?.isBookmarked ?: 0
                topAlbum.copy(isBookmarked = isBookmarked)
            }
        }
            .map<List<TopAlbum>, ApiState<List<TopAlbum>>> { Success(it) }
            .onStart { emit(ApiState.Loading) }
            .catch { e -> emit(ApiState.Error<Nothing>(e.message ?: "Unknown Error")) }
    }
}