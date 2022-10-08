/*
 * *
 *  * Created by Mohsen on 10/5/22, 2:45 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 10/5/22, 2:23 PM
 *
 */

package com.example.appsfactory.presentation.main

import androidx.lifecycle.viewModelScope
import com.example.appsfactory.data.source.local.entity.AlbumEntity
import com.example.appsfactory.domain.usecase.LocalAlbumsUseCase
import com.example.appsfactory.presentation.base.BaseViewModel
import com.example.appsfactory.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val albumsUseCase: LocalAlbumsUseCase
) : BaseViewModel<List<AlbumEntity>>() {

    init {
        viewModelScope.launch {
            albumsUseCase
                .getBookmarkedAlbums()
                .collect { album -> _uiState.value = UiState.Success(album) }
        }
    }
}