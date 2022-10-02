package com.example.appsfactory.ui.view.top_albums

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.appsfactory.data.local.entity.LocalAlbum
import com.example.appsfactory.data.local.repository.AlbumsRepository
import com.example.appsfactory.data.model.top_albums.Album
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

    fun onBookmarkClicked(album: Album) = viewModelScope.launch(ioDispatcher) {
        val mAlbum = LocalAlbum(
            id = album.playcount,
            name = album.name,
            artist = album.artist.name,
            image = album.image[2].text,
            url = album.url
        )

        albumsRepository.insert(mAlbum)
    }

    fun onBookmarkRemoveClicked(album: LocalAlbum) = viewModelScope.launch(ioDispatcher) {
        albumsRepository.delete(album)
    }
}