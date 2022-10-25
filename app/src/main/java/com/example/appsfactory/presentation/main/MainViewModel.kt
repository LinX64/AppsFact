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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    albumsUseCase: LocalAlbumsUseCase
) : BaseViewModel<List<AlbumEntity>>() {

    val mAlbums: StateFlow<List<AlbumEntity>> = albumsUseCase
        .getBookmarkedAlbums()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

}

sealed interface AlbumsState {
    object Loading : AlbumsState
    data class Success(val albums: List<AlbumEntity>) : AlbumsState
}