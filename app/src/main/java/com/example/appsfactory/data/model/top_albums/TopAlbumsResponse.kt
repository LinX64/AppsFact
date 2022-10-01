package com.example.appsfactory.data.model.top_albums


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class TopAlbumsResponse(
    @SerializedName("topalbums")
    val topalbums: Topalbums
)