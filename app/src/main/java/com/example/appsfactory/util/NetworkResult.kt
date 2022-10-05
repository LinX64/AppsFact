/*
 * *
 *  * Created by Mohsen on 10/5/22, 1:54 AM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 10/4/22, 1:13 PM
 *
 */

package com.example.appsfactory.util

sealed class NetworkResult<T> {
    data class Loading<T>(val loading: Boolean) : NetworkResult<T>()
    data class Success<T>(val data: T) : NetworkResult<T>()
    data class Error<T>(val error: String?) : NetworkResult<T>()
}