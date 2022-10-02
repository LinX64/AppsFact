package com.example.appsfactory.data.model.top_albums

import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Artist(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)