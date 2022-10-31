/*
 * *
 *  * Created by Mohsen on 10/4/22, 2:05 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 10/4/22, 2:05 PM
 *
 */

package com.example.appsfactory.domain.usecase

import com.example.appsfactory.data.source.local.entity.TopAlbumEntity
import com.example.appsfactory.domain.model.top_albums.TopAlbum
import com.example.appsfactory.domain.repository.AlbumRepository
import com.example.appsfactory.domain.repository.MainRepository
import com.example.appsfactory.util.ApiState
import com.example.appsfactory.util.ApiState.Success
import kotlinx.coroutines.flow.*
import java.util.Collections.emptyList
import javax.inject.Inject

class GetTopAlbumsUseCase @Inject constructor(
    private val mainRepository: MainRepository,
    private val localAlbumRepository: AlbumRepository
) {

    operator fun invoke(artistName: String): Flow<ApiState<List<TopAlbum>>> {
        val bookmarkedAlbums = localAlbumRepository.getBookmarkedAlbums()
        val remoteTopAlbums = mainRepository.getTopAlbumsBasedOnArtist2(artistName)


        return combine(remoteTopAlbums, bookmarkedAlbums) { topAlbums, bookmarked ->
            topAlbums.map { topAlbum ->
                val isBookmarked =
                    bookmarked.find { it.name == topAlbum.name }?.isBookmarked ?: 0
                topAlbum.copy(isBookmarked = isBookmarked)
            }
            Success(topAlbums)
        }
            .onStart { ApiState.Loading }
            .catch { e -> ApiState.Error<Nothing>(e.message.toString()) }


//        val combinedResponses = combinedResponses(remoteTopAlbums, bookmarkedAlbums)
//            .map { Success(it) }
//        // TODO: should be mapped to ApiState to be able to handle errors
//        return combinedResponses
    }

    private fun combinedResponses(
        remoteTopAlbums: Flow<ApiState<List<TopAlbum>>>,
        bookmarkedAlbums: Flow<List<TopAlbumEntity>>
    ): Flow<List<TopAlbum>> = remoteTopAlbums
        .map { apiState ->
            if (apiState is Success) apiState.data else emptyList()
        }
        .combine(bookmarkedAlbums) { remoteAlbums, localAlbums ->
            mapToTopAlbumEntity(remoteAlbums, localAlbums)
        }

    private fun mapToTopAlbumEntity(
        remoteAlbums: List<TopAlbum>, localAlbums: List<TopAlbumEntity>
    ) = remoteAlbums.map { topAlbum ->
        val isBookmarked = localAlbums.find { it.name == topAlbum.name }?.isBookmarked ?: 0
        topAlbum.copy(isBookmarked = isBookmarked)
    }
}
