package com.example.appsfactory.presentation.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.appsfactory.R
import com.example.appsfactory.domain.model.artistList.Image

object BindingAdapter {

    @JvmStatic
    @androidx.databinding.BindingAdapter("loadImageFromList")
    fun ImageView.loadImageFromList(imgUrlList: List<Image>) {
        val firstImgUrl = imgUrlList[0].text

        if (firstImgUrl.isNotEmpty()) {
            Glide.with(this.context)
                .load(firstImgUrl)
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
            .into(this)
    }
}