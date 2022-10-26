package com.example.appsfactory.domain.model.top_albums

import androidx.annotation.Keep
import com.example.appsfactory.data.source.local.entity.TopAlbumEntity
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
    val url: String,
    @SerializedName("isBookmarked")
    var isBookmarked: Int = 0
)

fun TopAlbum.toAlbumEntity() = TopAlbumEntity(
    id = playcount,
    name = name,
    artist = artist.name,
    image = image[2].text,
    isBookmarked = 1
)