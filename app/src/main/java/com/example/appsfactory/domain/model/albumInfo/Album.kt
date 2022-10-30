package com.example.appsfactory.domain.model.albumInfo

import androidx.annotation.Keep
import com.example.appsfactory.data.source.local.entity.AlbumInfoEntity
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
    @SerializedName("tags", alternate = [""])
    val tags: Tags?,
    @SerializedName("tracks")
    val tracks: Tracks?,
    @SerializedName("url")
    val url: String,
    @SerializedName("wiki")
    val wiki: Wiki?
)

fun Album.toEntity() = AlbumInfoEntity(
    id = playcount.toInt(),
    albumName = name,
    artistName = artist,
    image = image[2].text,
    tracks = tracks?.track.toString(),
    wiki = wiki?.summary ?: ""
)