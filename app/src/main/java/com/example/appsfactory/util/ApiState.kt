/*
 * *
 *  * Created by Mohsen on 10/26/22, 2:16 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 10/26/22, 2:16 PM
 *
 */

package com.example.appsfactory.util

sealed interface ApiState<out T> {
    object Loading : ApiState<Nothing>
    data class Success<T>(val data: T) : ApiState<T>
    data class Error<T>(val error: String, val data: T? = null) : ApiState<T>
}