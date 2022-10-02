package com.example.appsfactory.ui.view.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.appsfactory.data.local.repository.AlbumsRepository
import com.example.appsfactory.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val albumsRepository: AlbumsRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    fun getBookmarkedAlbums() = liveData(ioDispatcher) {
        val response = albumsRepository.getAlbums()
        response.collect { emit(it) }
    }

}