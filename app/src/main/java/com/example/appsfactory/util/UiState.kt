/*
 * *
 *  * Created by Mohsen on 10/5/22, 10:50 AM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 10/5/22, 10:50 AM
 *
 */

package com.example.appsfactory.util

sealed interface UiState<out T> {
    object Loading : UiState<Nothing>
    data class Success<T>(val data: T) : UiState<T>
    data class Error<T>(val error: String, val data: T? = null) : UiState<T>
}