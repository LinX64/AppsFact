/*
 * *
 *  * Created by Mohsen on 10/10/22, 1:05 AM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 10/10/22, 1:05 AM
 *
 */

package com.example.appsfactory.util

import kotlinx.coroutines.flow.*

inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean = { true }
) = flow {
    val data = query().first()

    val flow = if (shouldFetch(data)) {
        emit(Resource.Loading(data))

        try {
            saveFetchResult(fetch())
            query().map { Resource.Success(it) }
        } catch (throwable: Throwable) {
            query().map { Resource.Error(throwable, it) }
        }
    } else query().map { Resource.Success(it) }

    emitAll(flow)
}