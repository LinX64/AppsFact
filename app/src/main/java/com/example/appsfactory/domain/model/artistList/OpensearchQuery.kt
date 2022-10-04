package com.example.appsfactory.domain.model.artistList

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

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