package com.example.appsfactory.data.model.albumInfo

import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Artist(
    @SerializedName("mbid")
    val mbid: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)