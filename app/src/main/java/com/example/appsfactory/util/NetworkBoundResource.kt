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
        emit(ApiState.Loading(data))

        try {
            saveFetchResult(fetch())
            query().map { ApiState.Success(it) }
        } catch (throwable: Throwable) {
            query().map { ApiState.Error(throwable.message.toString(), it) }
        }
    } else query().map { ApiState.Success(it) }

    emitAll(flow)
}