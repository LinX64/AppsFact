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
import com.example.appsfactory.data.source.local.dao.AlbumsDao
import com.example.appsfactory.data.source.local.entity.LocalAlbum
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
    lateinit var albumsDao: AlbumsDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()

        albumsDao = db.albumDao()
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
        val album = LocalAlbum(
            name = "album",
            artist = "artist",
            image = "image",
            url = "url",
            isSelected = true
        )

        albumsDao.insertAlbum(album)
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