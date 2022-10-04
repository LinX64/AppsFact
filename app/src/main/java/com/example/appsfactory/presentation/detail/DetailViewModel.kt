package com.example.appsfactory.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.appsfactory.di.modules.IoDispatcher
import com.example.appsfactory.domain.repository.MainRepository
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