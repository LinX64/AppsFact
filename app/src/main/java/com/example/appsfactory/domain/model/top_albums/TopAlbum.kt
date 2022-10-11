package com.example.appsfactory.domain.model.top_albums

import androidx.annotation.Keep
import com.example.appsfactory.data.source.local.entity.AlbumEntity
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

fun List<TopAlbum>.asEntity(): List<AlbumEntity> {
    return map {
        AlbumEntity(
            it.playcount,
            it.name,
            it.artist.name,
            image = it.image[2].text
        )
    }
}