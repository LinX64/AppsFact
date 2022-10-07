package com.example.appsfactory.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "albums")
data class LocalAlbum(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "artist")
    val artist: String,
    @ColumnInfo(name = "image")
    val image: String,
    @ColumnInfo(name = "isBookmarked")
    val isBookmarked: Boolean = false,
)