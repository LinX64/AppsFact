package com.example.appsfactory.data.remote.api

import com.example.appsfactory.BuildConfig
import com.example.appsfactory.data.model.ArtistSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("?method=artist.search")
    suspend fun artistSearch(
        @Query("artist") artist: String,
        @Query("api_key") api_key: String = BuildConfig.API_KEY,
        @Query("format") format: String = "json"
    ): ArtistSearchResponse
}