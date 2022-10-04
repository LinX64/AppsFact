package com.example.appsfactory.domain.model.top_albums

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class TopAlbumsResponse(
    @SerializedName("topalbums")
    val topalbums: TopAlbums
)