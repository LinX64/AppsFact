/*
 * *
 *  * Created by Mohsen on 10/4/22, 3:18 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 10/4/22, 3:18 PM
 *
 */

package com.example.appsfactory.domain.usecase

import com.example.appsfactory.data.source.local.entity.AlbumInfoEntity
import com.example.appsfactory.domain.repository.AlbumInfoRepository
import com.example.appsfactory.util.ApiState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AlbumInfoUseCase @Inject constructor(
    private val albumInfoRepository: AlbumInfoRepository
) {

    operator fun invoke(
        id: Int,
        albumName: String,
        artistName: String
    ): Flow<ApiState<AlbumInfoEntity>> {
        return albumInfoRepository.getAlbumInfo(id, albumName, artistName)
    }
}