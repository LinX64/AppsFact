/*
 * *
 *  * Created by Mohsen on 10/4/22, 3:50 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 10/4/22, 3:21 PM
 *
 */

package com.example.appsfactory.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appsfactory.domain.model.artistList.Artist
import com.example.appsfactory.domain.usecase.SearchArtistUseCase
import com.example.appsfactory.presentation.search.ArtistListState.Loading
import com.example.appsfactory.presentation.search.ArtistListState.Success
import com.example.appsfactory.util.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchArtistUseCase: SearchArtistUseCase
) : ViewModel() {

    operator fun invoke(artistName: String): StateFlow<ArtistListState> {
        return searchArtistUseCase(artistName)
            .map { result -> handleState(result) }
            .map { state -> state as ArtistListState }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = Loading
            )
    }

    private fun handleState(result: ApiResult<List<Artist>>) =
        when (result) {
            is ApiResult.Loading -> Loading
            is ApiResult.Success -> Success(result.data)
            is ApiResult.Error -> Error(result.exception.toString())
        }
}

sealed interface ArtistListState {
    object Loading : ArtistListState
    data class Success(val artists: List<Artist>) : ArtistListState
    data class Error(val message: String) : ArtistListState
}