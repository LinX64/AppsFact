/*
 * *
 *  * Created by Mohsen on 10/10/22, 1:26 AM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 10/10/22, 1:26 AM
 *
 */

package com.example.appsfactory.util

sealed class ApiState<T>(val data: T? = null, val message: String? = null) {
    class Loading<T>(data: T? = null) : ApiState<T>(data)
    class Success<T>(data: T?) : ApiState<T>(data)
    class Error<T>(message: String, data: T? = null) : ApiState<T>(data, message)
}