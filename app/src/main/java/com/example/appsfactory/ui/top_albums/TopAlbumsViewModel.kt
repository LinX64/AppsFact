/*
 * *
 *  * Created by Mohsen on 10/5/22, 2:48 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 10/5/22, 2:47 PM
 *
 */

package com.example.appsfactory.ui.top_albums

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appsfactory.di.modules.IoDispatcher
import com.example.appsfactory.domain.model.top_albums.TopAlbum
import com.example.appsfactory.domain.model.top_albums.toAlbumEntity
import com.example.appsfactory.domain.usecase.GetTopAlbumsUseCase
import com.example.appsfactory.domain.usecase.LocalAlbumsUseCase
import com.example.appsfactory.util.UiState
import com.example.appsfactory.util.stateInViewModel
import com.example.appsfactory.util.toUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopAlbumsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    topAlbumsUseCase: GetTopAlbumsUseCase,
    private val localAlbumsUseCase: LocalAlbumsUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val artistName: String = savedStateHandle["artistName"] ?: ""
    val topAlbumsState = topAlbumsUseCase(artistName)
        .map { it.toUiState() }
        .stateInViewModel(viewModelScope, initialValue = UiState.Loading)

    fun onBookmarkClicked(album: TopAlbum) = viewModelScope.launch(ioDispatcher) {
        localAlbumsUseCase.insert(album.toAlbumEntity())
    }

    fun onBookmarkRemove(album: TopAlbum) = viewModelScope.launch(ioDispatcher) {
        localAlbumsUseCase.delete(album.playcount)
    }
}
