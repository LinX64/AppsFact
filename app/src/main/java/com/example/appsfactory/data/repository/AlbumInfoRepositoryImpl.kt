/*
 * *
 *  * Created by Mohsen on 10/8/22, 3:50 AM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 10/8/22, 3:39 AM
 *
 */

package com.example.appsfactory.data.repository

import androidx.room.withTransaction
import com.example.appsfactory.data.source.local.AppDatabase
import com.example.appsfactory.data.source.remote.ApiService
import com.example.appsfactory.domain.model.albumInfo.toEntity
import com.example.appsfactory.domain.repository.AlbumInfoRepository
import com.example.appsfactory.util.networkBoundResource
import javax.inject.Inject

class AlbumInfoRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val appDb: AppDatabase
) : AlbumInfoRepository {

    private val albumInfoDao = appDb.albumInfoDao()

    override fun getAlbumInfo(
        id: Int,
        albumName: String,
        artistName: String
    ) = networkBoundResource(
        query = {
            albumInfoDao.getAlbumInfo(id)
        },
        fetch = {
            apiService.fetchAlbumInfo(albumName, artistName).album.toEntity()
        },
        saveFetchResult = { album ->
            appDb.withTransaction {
                albumInfoDao.deleteAlbum(album)
                albumInfoDao.insert(album)
            }
        }
    )
}