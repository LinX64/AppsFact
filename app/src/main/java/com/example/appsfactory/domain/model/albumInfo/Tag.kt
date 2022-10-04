package com.example.appsfactory.domain.model.albumInfo

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Tag(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)