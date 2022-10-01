package com.example.appsfactory.data.model

import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

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