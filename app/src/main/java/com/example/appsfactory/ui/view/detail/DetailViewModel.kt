package com.example.appsfactory.ui.view.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.appsfactory.data.remote.repository.MainRepository
import com.example.appsfactory.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    fun getAlbumInfo(albumName: String, artistName: String) = liveData(ioDispatcher) {
        val response = mainRepository.getAlbumInfo(albumName, artistName)
        response.collect { emit(it) }
    }

}