package com.example.appsfactory.presentation.main

import androidx.lifecycle.viewModelScope
import com.example.appsfactory.data.source.local.entity.LocalAlbum
import com.example.appsfactory.domain.usecase.LocalAlbumsUseCase
import com.example.appsfactory.presentation.base.BaseViewModel
import com.example.appsfactory.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val albumsUseCase: LocalAlbumsUseCase
) : BaseViewModel<List<LocalAlbum>>() {

    init {
        viewModelScope.launch {
            albumsUseCase
                .getAlbums()
                .collect { album -> _uiState.value = UiState.Success(album) }
        }
    }
}