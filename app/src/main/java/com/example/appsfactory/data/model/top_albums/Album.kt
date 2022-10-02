package com.example.appsfactory.data.model.top_albums

import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Album(
    @SerializedName("artist")
    val artist: Artist,
    @SerializedName("image")
    val image: List<Image>,
    @SerializedName("name")
    val name: String,
    @SerializedName("playcount")
    val playcount: Int,
    @SerializedName("url")
    val url: String
)