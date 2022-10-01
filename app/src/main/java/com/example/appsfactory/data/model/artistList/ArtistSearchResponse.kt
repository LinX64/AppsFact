package com.example.appsfactory.data.model.artistList

import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class ArtistSearchResponse(
    @SerializedName("results")
    val results: Results
)