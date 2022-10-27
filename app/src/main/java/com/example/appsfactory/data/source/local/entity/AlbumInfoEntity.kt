package com.example.appsfactory.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "album_info")
data class AlbumInfoEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "albumName")
    val albumName: String,
    @ColumnInfo(name = "artistName")
    val artistName: String,
    @ColumnInfo(name = "image")
    val image: String,
    @ColumnInfo(name = "tracks")
    val tracks: String?,
    @ColumnInfo(name = "wiki")
    val wiki: String?
)