/*
 * *
 *  * Created by Mohsen on 10/5/22, 2:45 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 10/5/22, 2:23 PM
 *
 */

package com.example.appsfactory.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appsfactory.data.source.local.entity.TopAlbumEntity
import com.example.appsfactory.domain.usecase.LocalAlbumsUseCase
import com.example.appsfactory.util.stateInViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    albumsUseCase: LocalAlbumsUseCase
) : ViewModel() {

    val mAlbums: StateFlow<List<TopAlbumEntity>> = albumsUseCase
        .getBookmarkedAlbums()
        .stateInViewModel(viewModelScope, initialValue = emptyList())
}