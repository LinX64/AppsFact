package com.example.appsfactory.domain.model.albumInfo

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Track(
    @SerializedName("artist")
    val artist: com.example.appsfactory.domain.model.albumInfo.Artist,
    @SerializedName("@attr")
    val attr: com.example.appsfactory.domain.model.albumInfo.Attr,
    @SerializedName("duration")
    val duration: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("streamable")
    val streamable: com.example.appsfactory.domain.model.albumInfo.Streamable,
    @SerializedName("url")
    val url: String
)