/*
 * *
 *  * Created by Mohsen on 10/8/22, 3:51 AM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 10/8/22, 3:51 AM
 *
 */

package com.example.appsfactory.data.repository

import com.example.appsfactory.data.source.local.dao.TopAlbumsDao
import com.example.appsfactory.data.source.local.entity.TopAlbumEntity
import com.example.appsfactory.domain.repository.AlbumRepository
import javax.inject.Inject

class AlbumRepositoryImpl @Inject constructor(private val albumDao: TopAlbumsDao) :
    AlbumRepository {

    override fun getBookmarkedAlbums() = albumDao.getBookmarkedAlbums()

    override suspend fun insert(album: TopAlbumEntity) = albumDao.insert(album)

    override suspend fun delete(id: Int) = albumDao.deleteAlbum(id)
}