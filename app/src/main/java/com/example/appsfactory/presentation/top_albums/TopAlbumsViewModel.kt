/*
 * *
 *  * Created by Mohsen on 10/5/22, 2:48 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 10/5/22, 2:47 PM
 *
 */

package com.example.appsfactory.presentation.top_albums

import androidx.lifecycle.viewModelScope
import com.example.appsfactory.data.source.local.AppDatabase
import com.example.appsfactory.di.modules.IoDispatcher
import com.example.appsfactory.domain.model.top_albums.TopAlbum
import com.example.appsfactory.domain.usecase.AlbumInfoUseCase
import com.example.appsfactory.domain.usecase.GetTopAlbumsUseCase
import com.example.appsfactory.domain.usecase.LocalAlbumsUseCase
import com.example.appsfactory.presentation.base.BaseViewModel
import com.example.appsfactory.util.ApiState
import com.example.appsfactory.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopAlbumsViewModel @Inject constructor(
    private val topAlbumsUseCase: GetTopAlbumsUseCase,
    private val localAlbumsUseCase: LocalAlbumsUseCase,
    private val albumInfoUseCase: AlbumInfoUseCase,
    private val appDatabase: AppDatabase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : BaseViewModel<List<TopAlbum>>() {

    fun getTopAlbumsBasedOnArtist(artistName: String) = viewModelScope.launch {
        topAlbumsUseCase.getTopAlbumsBasedOnArtist(artistName).collect { handleState(it) }
    }

    private fun handleState(it: ApiState<List<TopAlbum>>) {
        when (it) {
            is ApiState.Loading -> _uiState.value = UiState.Loading
            is ApiState.Success -> _uiState.value = UiState.Success(it.data)
            is ApiState.Error -> _uiState.value = UiState.Error(it.error.toString())
        }
    }

    fun onBookmarkClicked(album: TopAlbum) = viewModelScope.launch(ioDispatcher) {
        localAlbumsUseCase.update(album.name, true)
    }

    fun onBookmarkRemoveClicked(album: TopAlbum) = viewModelScope.launch(ioDispatcher) {
        localAlbumsUseCase.delete(album.playcount)
        // TODO: 10/5/2021 remove from local db
    }
}