package com.example.appsfactory.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appsfactory.data.source.local.entity.LocalAlbum
import com.example.appsfactory.domain.usecase.LocalAlbumsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val albumsUseCase: LocalAlbumsUseCase
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<AlbumsUiState>(AlbumsUiState.Success(emptyList()))
    val uiState: StateFlow<AlbumsUiState> = _uiState

    init {
        viewModelScope.launch {
            albumsUseCase
                .getAlbums()
                .collect { album -> _uiState.value = AlbumsUiState.Success(album) }
        }
    }
}

sealed class AlbumsUiState {
    data class Loading(val isLoading: Boolean = true) : AlbumsUiState()
    data class Success(val albums: List<LocalAlbum>) : AlbumsUiState()
    data class Error(val exception: Throwable) : AlbumsUiState()
}

