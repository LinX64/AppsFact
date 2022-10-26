package com.example.appsfactory.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.appsfactory.data.source.local.entity.AlbumInfoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AlbumInfoDao {

    @Query("SELECT * FROM album_info")
    suspend fun getAll(): List<AlbumInfoEntity>

    @Query("SELECT * FROM album_info WHERE id = :id")
    fun getAlbumInfo(id: Int): Flow<AlbumInfoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(album: AlbumInfoEntity)

    @Query("DELETE FROM album_info")
    suspend fun deleteAll()
}