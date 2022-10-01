package com.example.appsfactory.data.model.artistList

import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class OpensearchQuery(
    @SerializedName("role")
    val role: String,
    @SerializedName("searchTerms")
    val searchTerms: String,
    @SerializedName("startPage")
    val startPage: String,
    @SerializedName("#text")
    val text: String
)