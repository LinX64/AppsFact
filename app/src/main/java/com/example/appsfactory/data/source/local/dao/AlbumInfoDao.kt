package com.example.appsfactory.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.appsfactory.data.source.local.entity.AlbumInfoEntity

@Dao
interface AlbumInfoDao {

    @Query("SELECT * FROM album_info")
    suspend fun getAll(): List<AlbumInfoEntity>

    @Query("SELECT * FROM album_info WHERE id = :id")
    suspend fun getAlbumInfo(id: Int): AlbumInfoEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(album: AlbumInfoEntity)

    @Query("DELETE FROM album_info")
    suspend fun deleteAll()
}