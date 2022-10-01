package com.example.appsfactory.util

import android.widget.ImageView
import com.bumptech.glide.Glide

object BindingAdapter {

    @JvmStatic
    @androidx.databinding.BindingAdapter("loadImage")
    fun ImageView.loadImage(image: String) {
        Glide.with(this.context)
            .load(image)
            .into(this)
    }
}