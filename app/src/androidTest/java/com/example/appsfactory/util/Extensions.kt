package com.example.appsfactory.util

import org.awaitility.Awaitility
import org.awaitility.core.ThrowingRunnable
import java.util.concurrent.TimeUnit

fun waitAndRetry(assertion: ThrowingRunnable) =
    Awaitility.await().pollDelay(500, TimeUnit.MILLISECONDS).atMost(6, TimeUnit.SECONDS)
        .untilAsserted(assertion)

fun awaitForAssertion(assertion: ThrowingRunnable) =
    Awaitility.await()
        .atMost(8, TimeUnit.SECONDS)
        .with().pollInterval(1, TimeUnit.SECONDS)
        .pollDelay(500, TimeUnit.MILLISECONDS)
        .untilAsserted(assertion)
