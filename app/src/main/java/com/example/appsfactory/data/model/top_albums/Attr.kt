package com.example.appsfactory.data.model.top_albums


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

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