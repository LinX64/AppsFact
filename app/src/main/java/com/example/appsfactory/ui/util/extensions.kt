/*
 * *
 *  * Created by Mohsen on 10/9/22, 5:06 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 10/9/22, 5:04 PM
 *
 */

package com.example.appsfactory.ui.util

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.inVisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.hideSoftInput() {
    val inputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
}

fun showSnackBar(
    activity: Activity,
    message: String,
    action: String? = null,
    actionListener: View.OnClickListener? = null,
    duration: Int = Snackbar.LENGTH_SHORT
) {
    val snackBar = Snackbar.make(activity.findViewById(android.R.id.content), message, duration)
        .setTextColor(Color.WHITE)
    if (action != null && actionListener != null) {
        snackBar.setAction(action, actionListener)
    }
    snackBar.show()
}

inline fun <reified T> Flow<T>.observeWithLifecycle(
    fragment: Fragment,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    noinline action: suspend (T) -> Unit
): Job = fragment.viewLifecycleOwner.lifecycleScope.launch {
    flowWithLifecycle(fragment.viewLifecycleOwner.lifecycle, minActiveState).collect(action)
}