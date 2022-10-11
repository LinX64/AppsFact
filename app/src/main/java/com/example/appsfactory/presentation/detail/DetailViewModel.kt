/*
 * *
 *  * Created by Mohsen on 10/5/22, 2:44 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 10/5/22, 2:23 PM
 *
 */

package com.example.appsfactory.presentation.detail

import androidx.lifecycle.viewModelScope
import com.example.appsfactory.data.source.local.entity.AlbumInfoEntity
import com.example.appsfactory.domain.usecase.AlbumInfoUseCase
import com.example.appsfactory.presentation.base.BaseViewModel
import com.example.appsfactory.util.ApiState
import com.example.appsfactory.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val albumInfoUseCase: AlbumInfoUseCase
) : BaseViewModel<AlbumInfoEntity>() {

    fun getAlbumInfo(id: Int, albumName: String, artistName: String) = viewModelScope.launch {
        albumInfoUseCase.getAlbumInfo(id, albumName, artistName).collect {
            when (it) {
                is ApiState.Loading -> _uiState.value = UiState.Loading
                is ApiState.Success -> {
                    if (it.data != null) _uiState.value = UiState.Success(it.data)
                }
                is ApiState.Error -> _uiState.value = UiState.Error(it.message.toString())
            }
        }
    }
}
