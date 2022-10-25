package com.example.appsfactory.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.appsfactory.data.source.local.dao.AlbumInfoDao
import com.example.appsfactory.data.source.local.dao.TopAlbumsDao
import com.example.appsfactory.data.source.local.entity.AlbumInfoEntity
import com.example.appsfactory.data.source.local.entity.TopAlbumEntity

@Database(
    version = 1,
    entities = [TopAlbumEntity::class, AlbumInfoEntity::class],
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun topAlbumDao(): TopAlbumsDao
    abstract fun albumInfoDao(): AlbumInfoDao
}