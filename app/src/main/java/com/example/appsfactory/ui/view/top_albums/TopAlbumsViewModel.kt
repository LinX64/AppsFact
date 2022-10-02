package com.example.appsfactory.ui.view.top_albums

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.appsfactory.data.local.entity.LocalAlbum
import com.example.appsfactory.data.local.repository.AlbumsRepository
import com.example.appsfactory.data.remote.repository.MainRepository
import com.example.appsfactory.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopAlbumsViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val albumsRepository: AlbumsRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    fun getTopAlbumsBasedOnArtist(artistName: String) = liveData(ioDispatcher) {
        val response = mainRepository.getTopAlbumsBasedOnArtist(artistName)
        response.collect { emit(it) }
    }

    fun onBookmarkClicked(album: LocalAlbum) = viewModelScope.launch(ioDispatcher) {
        albumsRepository.insert(album)
    }

    fun onBookmarkRemoveClicked(album: LocalAlbum) = viewModelScope.launch(ioDispatcher) {
        albumsRepository.delete(album)
    }
}