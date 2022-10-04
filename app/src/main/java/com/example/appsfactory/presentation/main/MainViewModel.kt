package com.example.appsfactory.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.appsfactory.di.modules.IoDispatcher
import com.example.appsfactory.domain.usecase.GetAlbumsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val albumsUseCase: GetAlbumsUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    fun getBookmarkedAlbums() = liveData(ioDispatcher) {
        val response = albumsUseCase.getAlbums()
        response.collect { emit(it) }
    }

}