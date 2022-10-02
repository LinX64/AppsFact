package com.example.appsfactory.data.local.repository

import com.example.appsfactory.data.local.dao.AlbumsDao
import com.example.appsfactory.data.local.entity.LocalAlbum
import com.example.appsfactory.data.model.top_albums.Album
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class AlbumsRepository @Inject constructor(private val albumsDao: AlbumsDao) {

    fun getAlbums() = albumsDao.getAll()

    suspend fun insert(albums: LocalAlbum) = albumsDao.insertAlbum(albums)

    suspend fun delete(album: LocalAlbum) = albumsDao.deleteAlbum(album)

}