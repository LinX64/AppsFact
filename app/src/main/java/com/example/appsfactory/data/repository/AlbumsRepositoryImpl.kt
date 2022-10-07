/*
 * *
 *  * Created by Mohsen on 10/4/22, 1:28 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 10/4/22, 1:28 PM
 *
 */

package com.example.appsfactory.data.repository

import com.example.appsfactory.data.source.local.dao.TopAlbumsDao
import com.example.appsfactory.domain.repository.AlbumRepository

class AlbumRepositoryImpl(private val albumDao: TopAlbumsDao) : AlbumRepository {

    override fun getAllTopAlbums() = albumDao.getAllAlbums()

    override suspend fun update(
        name: String,
        isBookmarked: Boolean
    ) = albumDao.update(name, isBookmarked)

    override suspend fun delete(id: Int) = albumDao.deleteAlbum(id)
}