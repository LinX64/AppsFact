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

abstract class BaseViewModel<T> : ViewModel() {
    protected val _uiState = MutableStateFlow<UiState<T>>(UiState.Loading)
    val uiState: StateFlow<UiState<T>> = _uiState
}