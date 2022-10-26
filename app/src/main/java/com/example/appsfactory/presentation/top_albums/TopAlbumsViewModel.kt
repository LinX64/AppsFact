/*
 * *
 *  * Created by Mohsen on 10/5/22, 2:48 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 10/5/22, 2:47 PM
 *
 */

package com.example.appsfactory.presentation.top_albums

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appsfactory.di.modules.IoDispatcher
import com.example.appsfactory.domain.model.top_albums.TopAlbum
import com.example.appsfactory.domain.model.top_albums.toAlbumEntity
import com.example.appsfactory.domain.usecase.GetTopAlbumsUseCase
import com.example.appsfactory.domain.usecase.LocalAlbumsUseCase
import com.example.appsfactory.presentation.top_albums.TopAlbumsState.*
import com.example.appsfactory.util.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopAlbumsViewModel @Inject constructor(
    private val topAlbumsUseCase: GetTopAlbumsUseCase,
    private val localAlbumsUseCase: LocalAlbumsUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    operator fun invoke(artistName: String) = topAlbumsUseCase(artistName)
        .map { result -> handleState(result) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = Loading
        )

    private fun handleState(result: ApiResult<List<TopAlbum>>) =
        when (result) {
            is ApiResult.Loading -> Loading
            is ApiResult.Success -> Success(result.data)
            is ApiResult.Error -> Error(result.exception.toString())
        }

    fun onBookmarkClicked(album: TopAlbum) = viewModelScope.launch(ioDispatcher) {
        localAlbumsUseCase.insert(album.toAlbumEntity())
    }

    fun onBookmarkRemoveClicked(album: TopAlbum) = viewModelScope.launch(ioDispatcher) {
        localAlbumsUseCase.delete(album.playcount)
    }
}

sealed interface TopAlbumsState {
    object Loading : TopAlbumsState
    data class Success(val data: List<TopAlbum>) : TopAlbumsState
    data class Error(val message: String) : TopAlbumsState
}