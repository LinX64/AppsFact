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
import com.example.appsfactory.data.source.local.dao.TopAlbumsDao
import com.example.appsfactory.data.source.local.entity.AlbumEntity
import kotlinx.coroutines.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
class DatabaseTest {

    @Inject
    lateinit var db: AppDatabase

    @Inject
    lateinit var albumsDao: TopAlbumsDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()

        albumsDao = db.topAlbumDao()
    }

    @Test
    fun testInsertAlbums() = runBlocking {
        val job = async(Dispatchers.IO) {
            insertDummyData()
        }

        finishTheJob(job)
    }

    @Test
    fun testDeleteAlbums() = runBlocking {
        val job = async(Dispatchers.IO) {
            db.clearAllTables()
        }

        finishTheJob(job)
    }

    private suspend fun insertDummyData() {
        val albums = List(10) {
            AlbumEntity(
                0,
                name = "Album $it",
                artist = "Artist $it",
                image = "https://lastfm.freetls.fastly.net/i/u/34s/2a96cbd8b46e442fc41c2be3b5b7e943.png",
                isBookmarked = false
            )
        }
        albumsDao.insertAll(albums)
    }

    private suspend fun finishTheJob(job: Deferred<Unit>) {
        job.await()
        job.cancelAndJoin()
    }

    private fun clearTables() {
        db.clearAllTables()
    }

    @After
    @Throws(IOException::class)
    fun closeAndClearDB() {
        clearTables()
        db.close()
    }
}