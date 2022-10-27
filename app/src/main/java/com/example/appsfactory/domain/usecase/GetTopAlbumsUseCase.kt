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
import com.example.appsfactory.presentation.top_albums.TopAlbumsState
import com.example.appsfactory.util.ApiResult
import kotlinx.coroutines.flow.*
import java.util.Collections.emptyList
import javax.inject.Inject

class GetTopAlbumsUseCase @Inject constructor(
    private val mainRepository: MainRepository,
    private val localAlbumRepository: AlbumRepository
) {

    operator fun invoke(artistName: String): Flow<TopAlbumsState> {
        return mainRepository.getTopAlbumsBasedOnArtist(artistName)
            .onStart { emit(ApiResult.Loading) }
            .map {
                if (it is ApiResult.Success) it.data else emptyList()
            }
            .filterNot { it.isEmpty() }
            .combine(localAlbumRepository.getBookmarkedAlbums()) { remoteAlbums, localAlbums ->
                mapToTopAlbumEntity(remoteAlbums, localAlbums)
            }
            .map {
                if (it.isNotEmpty()) TopAlbumsState.Success(it)
                else TopAlbumsState.Error("No data found")
            }
    }

    private fun mapToTopAlbumEntity(
        remoteAlbums: List<TopAlbum>,
        localAlbums: List<TopAlbumEntity>
    ) = remoteAlbums.map { topAlbum ->
        val isBookmarked =
            localAlbums.find { it.name == topAlbum.name }?.isBookmarked ?: 0

        topAlbum.copy(isBookmarked = isBookmarked)
    }

}
