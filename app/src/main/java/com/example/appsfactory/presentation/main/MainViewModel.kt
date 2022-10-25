/*
 * *
 *  * Created by Mohsen on 10/5/22, 2:45 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 10/5/22, 2:23 PM
 *
 */

package com.example.appsfactory.presentation.main

import androidx.lifecycle.viewModelScope
import com.example.appsfactory.data.source.local.entity.TopAlbumEntity
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
) : BaseViewModel<List<TopAlbumEntity>>() {

    val mAlbums: StateFlow<List<TopAlbumEntity>> = albumsUseCase
        .getBookmarkedAlbums()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )
}