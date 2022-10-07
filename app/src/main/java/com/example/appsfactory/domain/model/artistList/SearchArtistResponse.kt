package com.example.appsfactory.domain.model.artistList

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class SearchArtistResponse(
    @SerializedName("results")
    val results: Results
)