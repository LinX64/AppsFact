package com.example.appsfactory.domain.model.albumInfo

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Album(
    @SerializedName("artist")
    val artist: String,
    @SerializedName("image")
    val image: List<Image>,
    @SerializedName("listeners")
    val listeners: String,
    @SerializedName("mbid")
    val mbid: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("playcount")
    val playcount: String,
    @SerializedName("tags")
    val tags: Tags,
    @SerializedName("tracks")
    val tracks: Tracks,
    @SerializedName("url")
    val url: String,
    @SerializedName("wiki")
    val wiki: Wiki
)