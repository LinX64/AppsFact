/*
 * *
 *  * Created by Mohsen on 10/4/22, 1:30 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 10/4/22, 1:30 PM
 *
 */

package com.example.appsfactory.domain.repository

import com.example.appsfactory.data.source.local.entity.TopAlbumEntity
import kotlinx.coroutines.flow.Flow

interface AlbumRepository {

    fun getBookmarkedAlbums(): Flow<List<TopAlbumEntity>>

    suspend fun insert(album: TopAlbumEntity)

    suspend fun delete(id: Int)
}