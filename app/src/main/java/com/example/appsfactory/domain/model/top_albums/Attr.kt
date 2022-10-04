package com.example.appsfactory.domain.model.top_albums

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Attr(
    @SerializedName("artist")
    val artist: String,
    @SerializedName("page")
    val page: String,
    @SerializedName("perPage")
    val perPage: String,
    @SerializedName("total")
    val total: String,
    @SerializedName("totalPages")
    val totalPages: String
)