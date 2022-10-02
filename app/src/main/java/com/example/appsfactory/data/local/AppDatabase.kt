package com.example.appsfactory.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.appsfactory.data.local.dao.AlbumsDao
import com.example.appsfactory.data.local.entity.LocalAlbum

@Database(version = 1, entities = [LocalAlbum::class])
abstract class AppDatabase : RoomDatabase() {

    abstract fun albumDao(): AlbumsDao
}