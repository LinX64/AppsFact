package com.example.appsfactory.data.model.albumInfo

import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Tags(
    @SerializedName("tag")
    val tag: List<Tag>
)