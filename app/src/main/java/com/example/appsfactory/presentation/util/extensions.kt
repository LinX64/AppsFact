/*
 * *
 *  * Created by Mohsen on 10/9/22, 5:06 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 10/9/22, 5:04 PM
 *
 */

package com.example.appsfactory.presentation.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

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