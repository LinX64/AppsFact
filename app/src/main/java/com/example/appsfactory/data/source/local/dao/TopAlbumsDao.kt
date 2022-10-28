package com.example.appsfactory.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.appsfactory.data.source.local.entity.TopAlbumEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TopAlbumsDao {

    @Query("SELECT * FROM albums WHERE isBookmarked = 1")
    fun getBookmarkedAlbums(): Flow<List<TopAlbumEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(album: TopAlbumEntity)

    @Query("DELETE FROM albums WHERE id = :id")
    suspend fun deleteAlbum(id: Int)

    @Query("DELETE FROM albums")
    suspend fun deleteAll()
}