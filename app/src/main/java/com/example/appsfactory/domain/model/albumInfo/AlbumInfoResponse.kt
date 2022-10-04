package com.example.appsfactory.domain.model.albumInfo

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class AlbumInfoResponse(
    @SerializedName("album")
    val album: Album
)