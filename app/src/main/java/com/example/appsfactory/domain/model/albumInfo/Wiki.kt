package com.example.appsfactory.domain.model.albumInfo

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Wiki(
    @SerializedName("content")
    val content: String?,
    @SerializedName("published")
    val published: String,
    @SerializedName("summary")
    val summary: String?
)