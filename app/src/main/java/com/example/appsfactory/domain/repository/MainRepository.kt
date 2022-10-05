/*
 * *
 *  * Created by Mohsen on 10/4/22, 1:44 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 10/4/22, 1:40 PM
 *
 */

package com.example.appsfactory.domain.repository

import com.example.appsfactory.domain.model.albumInfo.Album
import com.example.appsfactory.domain.model.artistList.Artistmatches
import com.example.appsfactory.domain.model.top_albums.TopAlbum
import com.example.appsfactory.util.NetworkResult
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    suspend fun getArtist(artistName: String): Flow<NetworkResult<Artistmatches>>
    suspend fun getTopAlbumsBasedOnArtist(artistName: String): Flow<NetworkResult<List<TopAlbum>>>
    suspend fun getAlbumInfo(artistName: String, album: String): Flow<NetworkResult<Album>>
}