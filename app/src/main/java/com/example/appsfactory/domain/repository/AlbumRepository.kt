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
    fun getAllTopAlbums(): Flow<List<LocalAlbum>>
    suspend fun update(name: String, isBookmarked: Boolean)
    suspend fun delete(id: Int)
}