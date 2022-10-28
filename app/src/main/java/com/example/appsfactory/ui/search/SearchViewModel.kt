/*
 * *
 *  * Created by Mohsen on 10/4/22, 3:50 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 10/4/22, 3:21 PM
 *
 */

package com.example.appsfactory.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appsfactory.domain.model.artistList.Artist
import com.example.appsfactory.domain.usecase.SearchArtistUseCase
import com.example.appsfactory.ui.search.ArtistListState.Loading
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchArtistUseCase: SearchArtistUseCase
) : ViewModel() {

    operator fun invoke(artistName: String): StateFlow<ArtistListState> {
        return searchArtistUseCase(artistName)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = Loading
            )
    }
}

sealed interface ArtistListState {
    object Loading : ArtistListState
    data class Success(val artists: List<Artist>) : ArtistListState
    data class Error(val message: String) : ArtistListState
}