package com.example.appsfactory.presentation.util

import android.widget.ImageView
import com.bumptech.glide.Glide

object BindingAdapter {

    @JvmStatic
    @androidx.databinding.BindingAdapter("loadImage")
    fun ImageView.loadImage(image: String) {
        Glide.with(this.context)
            .load(image)
            .placeholder(android.R.drawable.ic_menu_gallery)
            .into(this)
    }
}