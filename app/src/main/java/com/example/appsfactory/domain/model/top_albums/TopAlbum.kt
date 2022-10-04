package com.example.appsfactory.domain.model.top_albums

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class TopAlbum(
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