/*
 * *
 *  * Created by Mohsen on 10/5/22, 2:48 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 10/5/22, 12:04 PM
 *
 */

package com.example.appsfactory.ui.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.appsfactory.R
import com.example.appsfactory.domain.model.artistList.Image

object BindingAdapter {

    @JvmStatic
    @androidx.databinding.BindingAdapter("loadImageFromList")
    fun ImageView.loadImageFromList(imgUrlList: List<Image>) {
        val firstImgUrl = imgUrlList[0].text

        if (imgUrlList.isNotEmpty()) {
            Glide.with(this.context)
                .load(firstImgUrl)
                .placeholder(R.drawable.ic_baseline_cloud_off_48)
                .into(this)
        } else Glide.with(this.context)
            .load(R.drawable.ic_baseline_cloud_off_48)
            .into(this)
    }

    @JvmStatic
    @androidx.databinding.BindingAdapter("loadImage")
    fun ImageView.loadImage(imgUrl: String) {
        Glide.with(this.context)
            .load(imgUrl)
            .placeholder(R.drawable.ic_baseline_cloud_off_48)
            .into(this)
    }
}