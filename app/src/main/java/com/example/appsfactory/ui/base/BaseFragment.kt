/*
 * *
 *  * Created by Mohsen on 10/5/22, 2:43 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 10/5/22, 11:50 AM
 *
 */

package com.example.appsfactory.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<VB : ViewBinding>(private val inflate: Inflate<VB>) : Fragment() {

    private var _binding: VB? = null
    val binding: VB get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflate(inflater, container, false)
        .also { _binding = it }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
    }

    protected open fun setupUI() {
        // Override this method to setup UI
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}