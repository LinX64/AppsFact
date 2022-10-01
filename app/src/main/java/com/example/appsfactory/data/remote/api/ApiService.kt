package com.example.appsfactory.data.remote.api

import com.example.appsfactory.BuildConfig.API_KEY
import com.example.appsfactory.data.model.artistList.ArtistSearchResponse
import com.example.appsfactory.data.model.top_albums.TopAlbumsResponse
import com.example.appsfactory.data.remote.util.Constants.FORMAT
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("?method=artist.search")
    suspend fun artistSearch(
        @Query("artist") artist: String,
        @Query("api_key") api_key: String = API_KEY,
        @Query("format") format: String = FORMAT
    ): ArtistSearchResponse

    @GET("?method=artist.gettopalbums")
    suspend fun getArtistTopAlbums(
        @Query("artist") artist: String,
        @Query("api_key") api_key: String = API_KEY,
        @Query("format") format: String = FORMAT
    ): TopAlbumsResponse
}