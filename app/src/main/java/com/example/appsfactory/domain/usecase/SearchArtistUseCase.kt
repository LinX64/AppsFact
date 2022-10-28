/*
 * *
 *  * Created by Mohsen on 10/4/22, 3:18 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 10/4/22, 3:18 PM
 *
 */

package com.example.appsfactory.domain.usecase

import com.example.appsfactory.domain.model.artistList.Artist
import com.example.appsfactory.domain.repository.MainRepository
import com.example.appsfactory.ui.search.ArtistListState
import com.example.appsfactory.util.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class SearchArtistUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {

    operator fun invoke(artistName: String): Flow<ArtistListState> {
        return mainRepository
            .getArtist(artistName)
            .map { result -> handleState(result) }
            .onStart { emit(ArtistListState.Loading) }
    }

    private fun handleState(result: ApiResult<List<Artist>>) = when (result) {
        is ApiResult.Success -> ArtistListState.Success(result.data)
        is ApiResult.Error -> ArtistListState.Error(result.error.toString())
        ApiResult.Loading -> ArtistListState.Loading
    }
}
