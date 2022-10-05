package com.example.appsfactory.presentation.top_albums

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.appsfactory.data.source.local.entity.LocalAlbum
import com.example.appsfactory.di.modules.IoDispatcher
import com.example.appsfactory.domain.model.top_albums.TopAlbum
import com.example.appsfactory.domain.usecase.GetTopAlbumsUseCase
import com.example.appsfactory.domain.usecase.LocalAlbumsUseCase
import com.example.appsfactory.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopAlbumsViewModel @Inject constructor(
    private val topAlbumsUseCase: GetTopAlbumsUseCase,
    private val localAlbumsUseCase: LocalAlbumsUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    fun getTopAlbumsBasedOnArtist(artistName: String) = liveData(ioDispatcher) {
        topAlbumsUseCase.invoke(artistName).collect {
            when (it) {
                is NetworkResult.Loading -> emit(TopAlbumsUiState.Loading(true))
                is NetworkResult.Success -> emit(TopAlbumsUiState.Success(it.data))
                is NetworkResult.Error -> emit(TopAlbumsUiState.Error(it.error.toString()))
            }
        }
    }

    fun onBookmarkClicked(album: TopAlbum) = viewModelScope.launch(ioDispatcher) {
        val mAlbum = LocalAlbum(
            name = album.name,
            artist = album.artist.name,
            image = album.image[2].text,
            url = album.url,
            isSelected = true
        )

        localAlbumsUseCase.insert(mAlbum)
    }

    fun onBookmarkRemoveClicked(album: TopAlbum) = viewModelScope.launch(ioDispatcher) {
        val mAlbum = LocalAlbum(
            name = album.name,
            artist = album.artist.name,
            image = album.image[2].text,
            url = album.url,
            isSelected = false
        )
        localAlbumsUseCase.delete(mAlbum)
    }
}

sealed class TopAlbumsUiState {
    data class Loading(val isLoading: Boolean) : TopAlbumsUiState()
    data class Success(val albums: List<TopAlbum>) : TopAlbumsUiState()
    data class Error(val exception: String) : TopAlbumsUiState()
}
