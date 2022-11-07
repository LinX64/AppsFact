/*
 * *
 *  * Created by Mohsen on 10/6/22, 12:15 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 10/6/22, 12:15 PM
 *
 */
package com.example.appsfactory

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.appsfactory.data.source.local.AppDatabase
import com.example.appsfactory.data.source.local.dao.AlbumInfoDao
import com.example.appsfactory.data.source.local.dao.TopAlbumsDao
import com.example.appsfactory.data.source.local.entity.AlbumInfoEntity
import com.example.appsfactory.data.source.local.entity.TopAlbumEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
class DatabaseTest {

    private val artistName = "Justin Bieber"
    private val mAlbumName = "Purpose (Deluxe)"

    @Inject
    lateinit var db: AppDatabase

    @Inject
    lateinit var albumsDao: TopAlbumsDao

    @Inject
    lateinit var albumInfoDao: AlbumInfoDao

    @Before
    fun setUp() {
        setupDb()

        albumsDao = db.topAlbumDao()
        albumInfoDao = db.albumInfoDao()
    }

    @Test
    fun testInsertTopAlbum() = runBlocking {
        insertTopAlbum()

        albumsDao.getBookmarkedAlbums().first().let {
            val expected = it.first().name
            assert(expected == mAlbumName)
        }
    }

    @Test
    fun testDeleteAlbums() = runBlocking {
        clearTables()

        albumsDao.getBookmarkedAlbums().first().let {
            assert(it.isEmpty())
        }
    }

    @Test
    fun testGetAlbumInfo() = runBlocking {
        insertDummyAlbumInfo()

        val expectedList = albumInfoDao.getAll()

        assert(expectedList.isNotEmpty())
    }

    private suspend fun insertDummyAlbumInfo() {
        val albumInfo = AlbumInfoEntity(
            1,
            albumName = mAlbumName,
            artistName = artistName,
            image = "albumImageUrl",
            tracks = "tracks",
            wiki = "wiki"
        )
        albumInfoDao.insert(albumInfo)
    }

    private suspend fun insertTopAlbum() {
        val albums = TopAlbumEntity(
            1,
            name = mAlbumName,
            artist = artistName,
            image = "https://lastfm.freetls.fastly.net/i/u/34s/2a96cbd8b46e442fc41c2be3b5b7e943.png",
            isBookmarked = 1
        )

        albumsDao.insert(albums)
    }

    private fun clearTables() {
        db.clearAllTables()
    }

    private fun setupDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "apps_factory_db"
        )
            .allowMainThreadQueries()
            .build()
    }

    @After
    fun closeAndClearDB() {
        clearTables()
        db.close()
    }
}