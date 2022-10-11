/*
 * *
 *  * Created by Mohsen on 10/10/22, 1:26 AM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 10/10/22, 1:26 AM
 *
 */

package com.example.appsfactory.util

typealias SimpleResource = Resource<Unit>

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Success<T>(data: T?) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
}