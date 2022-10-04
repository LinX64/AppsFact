/*
 * *
 *  * Created by Mohsen on 10/4/22, 2:05 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 10/4/22, 2:05 PM
 *
 */

package com.example.appsfactory.domain.usecase

import com.example.appsfactory.domain.repository.AlbumRepository
import javax.inject.Inject

class GetAlbumsUseCase @Inject constructor(private val albumRepository: AlbumRepository) {
    fun getAlbums() = albumRepository.getAlbums()
}
