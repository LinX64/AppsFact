/*
 * *
 *  * Created by Mohsen on 10/4/22, 1:44 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 10/4/22, 1:40 PM
 *
 */

package com.example.appsfactory.domain.repository

import com.example.appsfactory.domain.model.artistList.Artist
import com.example.appsfactory.domain.model.top_albums.TopAlbum
import com.example.appsfactory.util.ApiState
import kotlinx.coroutines.flow.Flow

interface MainRepository {

    fun getArtist(artistName: String): Flow<ApiState<List<Artist>>>

    fun getTopAlbumsBasedOnArtist(artistName: String): Flow<List<TopAlbum>>
}