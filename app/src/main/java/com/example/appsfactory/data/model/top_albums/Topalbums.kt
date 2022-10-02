package com.example.appsfactory.data.model.top_albums

import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Topalbums(
    @SerializedName("album")
    val album: List<Album>,
    @SerializedName("@attr")
    val attr: Attr
)