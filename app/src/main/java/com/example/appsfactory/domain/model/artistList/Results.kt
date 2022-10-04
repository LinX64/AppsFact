package com.example.appsfactory.domain.model.artistList

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Results(
    @SerializedName("artistmatches")
    val artistmatches: Artistmatches,
    @SerializedName("@attr")
    val attr: Attr,
    @SerializedName("opensearch:itemsPerPage")
    val opensearchItemsPerPage: String,
    @SerializedName("opensearch:Query")
    val opensearchQuery: OpensearchQuery,
    @SerializedName("opensearch:startIndex")
    val opensearchStartIndex: String,
    @SerializedName("opensearch:totalResults")
    val opensearchTotalResults: String
)