package com.example.appsfactory.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.appsfactory.data.source.local.dao.TopAlbumsDao
import com.example.appsfactory.data.source.local.entity.LocalAlbum

@Database(version = 1, entities = [LocalAlbum::class], exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun topAlbumDao(): TopAlbumsDao
}