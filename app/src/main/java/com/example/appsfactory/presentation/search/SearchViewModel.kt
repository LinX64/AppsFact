/*
 * *
 *  * Created by Mohsen on 10/4/22, 3:50 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 10/4/22, 3:21 PM
 *
 */

package com.example.appsfactory.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.appsfactory.di.modules.IoDispatcher
import com.example.appsfactory.domain.usecase.SearchArtistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchArtistUseCase: SearchArtistUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    fun getArtist(artistName: String) = liveData(ioDispatcher) {
        val response = searchArtistUseCase.invoke(artistName)
        response.collect { emit(it) }
    }
}