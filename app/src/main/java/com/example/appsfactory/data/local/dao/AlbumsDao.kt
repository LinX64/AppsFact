package com.example.appsfactory.data.local.dao

import androidx.room.*
import com.example.appsfactory.data.local.entity.LocalAlbum
import kotlinx.coroutines.flow.Flow

@Dao
interface AlbumsDao {

    @Query("SELECT * FROM albums")
    fun getAll(): Flow<List<LocalAlbum>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlbum(albums: LocalAlbum)

    @Delete
    suspend fun deleteAlbum(album: LocalAlbum)

    @Query("DELETE FROM albums")
    suspend fun deleteAll()
}