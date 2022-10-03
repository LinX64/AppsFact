package com.example.appsfactory.util

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.RecyclerViewActions

fun clickOnFirstItem(): ViewAction =
    RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
        0,
        ViewActions.click()
    )