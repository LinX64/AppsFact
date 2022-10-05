/*
 * *
 *  * Created by Mohsen on 10/5/22, 1:58 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 10/5/22, 1:58 PM
 *
 */

package com.example.appsfactory.presentation.base

import androidx.lifecycle.ViewModel
import com.example.appsfactory.util.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

open class BaseViewModel<T> : ViewModel() {
    protected val _uiState = MutableStateFlow<UiState<T>>(UiState.Loading)
    val uiState: StateFlow<UiState<T>> = _uiState

    /*init {
        when (uiState.value) {
            is UiState.Loading -> _uiState.value = UiState.Loading
            is UiState.Success -> _uiState.value = UiState.Success((uiState.value as UiState.Success<T>).data)
            is UiState.Error -> _uiState.value = UiState.Error(uiState.value.error)
        }
    }

    protected open fun onLoading() {
        _uiState.value = UiState.Loading
    }

    protected open fun onSuccess(data: T) {
        _uiState.value = UiState.Success(data)
    }

    protected open fun onError(error: Throwable) {
        _uiState.value = UiState.Error(error.toString())
    }*/

}