package com.example.appsfactory.data.source.remote

import com.example.appsfactory.BuildConfig.API_KEY
import com.example.appsfactory.domain.model.albumInfo.AlbumInfoResponse
import com.example.appsfactory.domain.model.artistList.SearchArtistResponse
import com.example.appsfactory.domain.model.top_albums.TopAlbumsResponse
import com.example.appsfactory.util.Constants.FORMAT
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("?method=artist.search")
    suspend fun getArtist(
        @Query("artist") artist: String,
        @Query("api_key") api_key: String = API_KEY,
        @Query("format") format: String = FORMAT
    ): SearchArtistResponse

    @GET("?method=artist.gettopalbums")
    suspend fun getTopAlbumsBasedOnArtist(
        @Query("artist") artist: String,
        @Query("api_key") api_key: String = API_KEY,
        @Query("format") format: String = FORMAT
    ): TopAlbumsResponse

    @GET("?method=album.getinfo")
    suspend fun fetchAlbumInfo(
        @Query("album") albumName: String,
        @Query("artist") artistName: String,
        @Query("api_key") api_key: String = API_KEY,
        @Query("format") format: String = FORMAT
    ): AlbumInfoResponse
}