package com.example.appsfactory.data.source.local.dao

import androidx.room.*
import com.example.appsfactory.data.source.local.entity.LocalAlbum
import kotlinx.coroutines.flow.Flow

@Dao
interface TopAlbumsDao {

    @Query("SELECT * FROM albums")
    fun getAllAlbums(): Flow<List<LocalAlbum>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(albums: List<LocalAlbum>)

    @Query("UPDATE albums SET isBookmarked = :isBookmarked WHERE name = :name")
    suspend fun update(name: String, isBookmarked: Boolean)

    @Delete
    suspend fun deleteAlbum(album: LocalAlbum)

    @Query("DELETE FROM albums")
    suspend fun deleteAll()
}