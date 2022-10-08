/*
 * *
 *  * Created by Mohsen on 10/4/22, 3:50 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 10/4/22, 3:21 PM
 *
 */

package com.example.appsfactory.presentation.search

import androidx.lifecycle.viewModelScope
import com.example.appsfactory.domain.model.artistList.Artistmatches
import com.example.appsfactory.domain.usecase.SearchArtistUseCase
import com.example.appsfactory.presentation.base.BaseViewModel
import com.example.appsfactory.util.ApiState
import com.example.appsfactory.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchArtistUseCase: SearchArtistUseCase
) : BaseViewModel<Artistmatches>() {

    fun getArtist(artistName: String) {
        viewModelScope.launch {
            searchArtistUseCase.getArtist(artistName).collect { handleState(it) }
        }
    }

    private fun handleState(it: ApiState<Artistmatches>) {
        when (it) {
            is ApiState.Loading -> _uiState.value = UiState.Loading
            is ApiState.Success -> _uiState.value = UiState.Success(it.data)
            is ApiState.Error -> _uiState.value = UiState.Error(it.error.toString())
        }
    }
}