package com.example.appsfactory.domain.model.albumInfo

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Artist(
    @SerializedName("mbid")
    val mbid: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)