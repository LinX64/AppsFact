package com.example.appsfactory.data.model.artistList

import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Image(
    @SerializedName("size")
    val size: String,
    @SerializedName("#text")
    val text: String
)