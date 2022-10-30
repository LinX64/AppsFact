/*
 * *
 *  * Created by Mohsen on 10/5/22, 2:44 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 10/5/22, 2:23 PM
 *
 */

package com.example.appsfactory.ui.info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appsfactory.domain.usecase.AlbumInfoUseCase
import com.example.appsfactory.util.UiState
import com.example.appsfactory.util.toUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class InfoViewModel @Inject constructor(
    private val albumInfoUseCase: AlbumInfoUseCase
) : ViewModel() {

    operator fun invoke(
        id: Int,
        albumName: String,
        artistName: String
    ) = albumInfoUseCase(id, albumName, artistName)
        .map { apiResult -> apiResult.toUiState() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = UiState.Loading
        )
}