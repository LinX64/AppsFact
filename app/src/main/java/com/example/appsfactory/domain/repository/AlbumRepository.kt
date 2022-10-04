/*
 * *
 *  * Created by Mohsen on 10/4/22, 1:30 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 10/4/22, 1:30 PM
 *
 */

package com.example.appsfactory.domain.repository

import com.example.appsfactory.data.source.local.entity.LocalAlbum
import kotlinx.coroutines.flow.Flow

interface AlbumRepository {
    suspend fun getAlbums(): Flow<List<LocalAlbum>>
    suspend fun insert(album: LocalAlbum)
    suspend fun delete(album: LocalAlbum)
}