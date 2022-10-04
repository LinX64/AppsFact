package com.example.appsfactory.domain.model.top_albums

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class TopAlbums(
    @SerializedName("album")
    val album: List<TopAlbum>,
    @SerializedName("@attr")
    val attr: Attr
)