/*
 * *
 *  * Created by Mohsen on 10/30/22, 4:30 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 10/30/22, 4:30 PM
 *
 */

package com.example.appsfactory.util

import com.example.appsfactory.util.ApiState.*

fun <T> ApiState<T>.toUiState() = when (this) {
    is Success<T> -> UiState.Success(data)
    is Error<T> -> UiState.Error(error, data)
    is Loading -> UiState.Loading
}

