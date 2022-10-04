package com.example.appsfactory.util

import android.view.View
import androidx.annotation.IdRes
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import org.awaitility.Awaitility
import org.awaitility.core.ThrowingRunnable
import org.hamcrest.CoreMatchers.any
import org.hamcrest.Matcher
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

fun <T : View> recyclerChildAction(@IdRes id: Int, block: T.() -> Unit): ViewAction {
    return object : ViewAction {
        override fun getConstraints(): Matcher<View> {
            return any(View::class.java)
        }

        override fun getDescription(): String {
            return "Performing action on RecyclerView child item"
        }

        override fun perform(
            uiController: UiController,
            view: View
        ) {
            view.findViewById<T>(id).block()
        }
    }

}