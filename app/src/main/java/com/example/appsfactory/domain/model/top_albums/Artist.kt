package com.example.appsfactory.domain.model.top_albums

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Artist(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)