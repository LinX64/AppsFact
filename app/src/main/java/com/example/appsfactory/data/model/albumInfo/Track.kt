package com.example.appsfactory.data.model.albumInfo

import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Track(
    @SerializedName("artist")
    val artist: Artist,
    @SerializedName("@attr")
    val attr: Attr,
    @SerializedName("duration")
    val duration: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("streamable")
    val streamable: Streamable,
    @SerializedName("url")
    val url: String
)