/*
 * *
 *  * Created by Mohsen on 10/4/22, 1:28 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 10/4/22, 1:28 PM
 *
 */

package com.example.appsfactory.data.repository

import com.example.appsfactory.data.source.local.dao.AlbumsDao
import com.example.appsfactory.data.source.local.entity.LocalAlbum
import com.example.appsfactory.domain.repository.AlbumRepository

class AlbumRepositoryImpl(private val albumDao: AlbumsDao) : AlbumRepository {
    override fun getAlbums() = albumDao.getAllAlbums()
    override suspend fun insert(album: LocalAlbum) = albumDao.insertAlbum(album)
    override suspend fun delete(album: LocalAlbum) = albumDao.deleteAlbum(album)
}