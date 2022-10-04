package com.example.appsfactory.domain.model.albumInfo

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Image(
    @SerializedName("size")
    val size: String,
    @SerializedName("#text")
    val text: String
)