/*
 * *
 *  * Created by Mohsen on 10/26/22, 3:42 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 10/26/22, 3:42 PM
 *
 */

package com.example.appsfactory.util

import com.example.appsfactory.di.modules.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean = { true },
    @IoDispatcher ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) = flow {
    val data = query().first()

    val flow = if (shouldFetch(data)) {
        emit(ApiState.Loading)

        try {
            saveFetchResult(fetch())
            query().map { ApiState.Success(it) }
        } catch (throwable: Throwable) {
            query().map { ApiState.Error(throwable.message.toString(), it) }
        }

    } else query().map { ApiState.Success(it) }

    emitAll(flow)
}
    .onStart { emit(ApiState.Loading) }
    .catch { e -> emit(ApiState.Error(e.message.toString(), null)) }
    .flowOn(ioDispatcher)