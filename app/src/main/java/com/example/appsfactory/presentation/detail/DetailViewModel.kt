package com.example.appsfactory.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.appsfactory.di.modules.IoDispatcher
import com.example.appsfactory.domain.model.albumInfo.Album
import com.example.appsfactory.domain.usecase.AlbumDetailUseCase
import com.example.appsfactory.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val albumDetailUseCase: AlbumDetailUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    fun getAlbumInfo(albumName: String, artistName: String) = liveData(ioDispatcher) {
        albumDetailUseCase.invoke(albumName, artistName).collect {
            when (it) {
                is NetworkResult.Loading -> emit(AlbumUiState.Loading(true))
                is NetworkResult.Success -> emit(AlbumUiState.Success(it.data))
                is NetworkResult.Error -> emit(AlbumUiState.Error(it.error.toString()))
            }
        }
    }

}

sealed class AlbumUiState {
    data class Loading(val isLoading: Boolean = true) : AlbumUiState()
    data class Success(val album: Album) : AlbumUiState()
    data class Error(val exception: String) : AlbumUiState()
}
