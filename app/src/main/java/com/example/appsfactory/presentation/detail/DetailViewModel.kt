package com.example.appsfactory.presentation.detail

import androidx.lifecycle.viewModelScope
import com.example.appsfactory.domain.model.albumInfo.Album
import com.example.appsfactory.domain.usecase.AlbumDetailUseCase
import com.example.appsfactory.presentation.base.BaseViewModel
import com.example.appsfactory.util.ApiState
import com.example.appsfactory.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val albumDetailUseCase: AlbumDetailUseCase
) : BaseViewModel<Album>() {

    fun getAlbumInfo(albumName: String, artistName: String) = viewModelScope.launch {
        albumDetailUseCase.getAlbumInfo(albumName, artistName).collect {
            when (it) {
                is ApiState.Loading -> _uiState.value = UiState.Loading
                is ApiState.Success -> _uiState.value = UiState.Success(it.data)
                is ApiState.Error -> _uiState.value = UiState.Error(it.error.toString())
            }
        }
    }

}
