/*
 * *
 *  * Created by Mohsen on 10/26/22, 2:16 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 10/26/22, 2:16 PM
 *
 */

package com.example.appsfactory.util

sealed interface ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>
    data class Error(val error: String? = null) : ApiResult<Nothing>
    object Loading : ApiResult<Nothing>
}