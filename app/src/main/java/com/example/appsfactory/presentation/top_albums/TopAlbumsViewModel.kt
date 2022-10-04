package com.example.appsfactory.presentation.top_albums

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.appsfactory.data.source.local.entity.LocalAlbum
import com.example.appsfactory.di.modules.IoDispatcher
import com.example.appsfactory.domain.model.top_albums.TopAlbum
import com.example.appsfactory.domain.usecase.GetTopAlbumsUseCase
import com.example.appsfactory.domain.usecase.LocalAlbumsUseCase
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
        val response = topAlbumsUseCase.getTopAlbumsBasedOnArtist(artistName)
        response.collect { emit(it) }
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