package com.example.appsfactory.domain.model.albumInfo

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Tags(
    @SerializedName("tag")
    val tag: List<Tag>
)