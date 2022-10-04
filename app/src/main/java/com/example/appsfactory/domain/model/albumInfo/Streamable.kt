package com.example.appsfactory.domain.model.albumInfo

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Streamable(
    @SerializedName("fulltrack")
    val fulltrack: String,
    @SerializedName("#text")
    val text: String
)