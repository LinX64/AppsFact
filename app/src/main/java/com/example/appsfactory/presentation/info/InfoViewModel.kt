/*
 * *
 *  * Created by Mohsen on 10/5/22, 2:44 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 10/5/22, 2:23 PM
 *
 */

package com.example.appsfactory.presentation.info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appsfactory.data.source.local.entity.AlbumInfoEntity
import com.example.appsfactory.domain.usecase.AlbumInfoUseCase
import com.example.appsfactory.presentation.info.AlbumInfoState.Loading
import com.example.appsfactory.presentation.info.AlbumInfoState.Success
import com.example.appsfactory.util.ApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class InfoViewModel @Inject constructor(
    private val albumInfoUseCase: AlbumInfoUseCase
) : ViewModel() {

    operator fun invoke(
        id: Int,
        albumName: String,
        artistName: String
    ): StateFlow<AlbumInfoState> {
        return albumInfoUseCase(id, albumName, artistName)
            .map {
                if (it is ApiState.Success && it.data != null) Success(it.data) else Success(it.data!!)
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = Loading
            )
    }
}

sealed interface AlbumInfoState {
    object Loading : AlbumInfoState
    class Success(val data: AlbumInfoEntity) : AlbumInfoState
    class Error(val message: String) : AlbumInfoState
}

