package com.example.appsfactory.ui.view.main

import androidx.lifecycle.ViewModel
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


}