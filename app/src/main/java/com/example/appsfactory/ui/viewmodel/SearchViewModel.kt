package com.example.appsfactory.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.appsfactory.data.remote.repository.MainRepository
import com.example.appsfactory.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    fun getArtist(artistName: String) = liveData(ioDispatcher) {
        val response = mainRepository.getArtist(artistName)
        response.collect { emit(it) }
    }

    fun getTopAlbumsBasedOnArtist(artistName: String) = liveData(ioDispatcher) {
        val response = mainRepository.getTopAlbumsBasedOnArtist(artistName)
        response.collect { emit(it) }
    }
}