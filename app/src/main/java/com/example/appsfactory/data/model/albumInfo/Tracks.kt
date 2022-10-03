package com.example.appsfactory.data.model.albumInfo

import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Tracks(
    @SerializedName("track")
    val track: List<Track>
)