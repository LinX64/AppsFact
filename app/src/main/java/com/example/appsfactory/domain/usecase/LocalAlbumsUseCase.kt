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

class LocalAlbumsUseCase @Inject constructor(
    private val albumRepository: AlbumRepository
) {

    fun getBookmarkedAlbums() = albumRepository.getBookmarkedAlbums()

    suspend fun update(name: String, isBookmarked: Boolean) =
        albumRepository.update(name, isBookmarked)

    suspend fun delete(id: Int) = albumRepository.delete(id)
}
