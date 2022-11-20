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
import com.example.appsfactory.domain.usecase.SearchArtistUseCase
import com.example.appsfactory.util.UiState
import com.example.appsfactory.util.stateInViewModel
import com.example.appsfactory.util.toUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchArtistUseCase: SearchArtistUseCase
) : ViewModel() {

    operator fun invoke(artistName: String) = searchArtistUseCase(artistName)
        .map { it.toUiState() }
        .stateInViewModel(viewModelScope, initialValue = UiState.Loading)
}