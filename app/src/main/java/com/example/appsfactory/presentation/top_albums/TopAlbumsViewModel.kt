/*
 * *
 *  * Created by Mohsen on 10/5/22, 2:48 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 10/5/22, 2:47 PM
 *
 */

package com.example.appsfactory.presentation.top_albums

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appsfactory.di.modules.IoDispatcher
import com.example.appsfactory.domain.model.top_albums.TopAlbum
import com.example.appsfactory.domain.model.top_albums.toAlbumEntity
import com.example.appsfactory.domain.usecase.GetTopAlbumsUseCase
import com.example.appsfactory.domain.usecase.LocalAlbumsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopAlbumsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val topAlbumsUseCase: GetTopAlbumsUseCase,
    private val localAlbumsUseCase: LocalAlbumsUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val artistName: String = savedStateHandle["artistName"] ?: ""

    val topAlbumsState: StateFlow<TopAlbumsState> = topAlbumsUseCase(artistName)
        .map { state -> handleState(state) }
        .catch { e -> TopAlbumsState.Error(e.message.toString()) }
        .flowOn(ioDispatcher)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = TopAlbumsState.Loading
        )

    private fun handleState(state: TopAlbumsState) =
        when (state) {
            is TopAlbumsState.Success -> TopAlbumsState.Success(state.data)
            is TopAlbumsState.Error -> TopAlbumsState.Error(state.message)
            is TopAlbumsState.Loading -> TopAlbumsState.Loading
        }

    fun onBookmarkClicked(album: TopAlbum) = viewModelScope.launch(ioDispatcher) {
        localAlbumsUseCase.insert(album.toAlbumEntity())
    }

    fun onBookmarkRemoveClicked(album: TopAlbum) = viewModelScope.launch(ioDispatcher) {
        localAlbumsUseCase.delete(album.playcount)
    }
}

