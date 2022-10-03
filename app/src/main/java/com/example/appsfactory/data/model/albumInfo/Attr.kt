package com.example.appsfactory.data.model.albumInfo

import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Attr(
    @SerializedName("rank")
    val rank: Int
)