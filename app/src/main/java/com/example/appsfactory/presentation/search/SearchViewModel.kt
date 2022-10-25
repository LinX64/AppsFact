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
import com.example.appsfactory.util.ApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchArtistUseCase: SearchArtistUseCase
) : ViewModel() {

    operator fun invoke(artistName: String): StateFlow<List<Artist>> {
        return searchArtistUseCase(artistName)
            .filterNot { it.data?.isEmpty() ?: true }
            .map { if (it is ApiState.Success) it.data ?: emptyList() else emptyList() }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )
    }
}
