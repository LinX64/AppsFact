/*
 * *
 *  * Created by Mohsen on 10/4/22, 2:05 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 10/4/22, 2:05 PM
 *
 */

package com.example.appsfactory.domain.usecase

import com.example.appsfactory.domain.model.top_albums.TopAlbum
import com.example.appsfactory.domain.repository.MainRepository
import com.example.appsfactory.util.ApiResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTopAlbumsUseCase @Inject constructor(private val mainRepository: MainRepository) {

    operator fun invoke(artistName: String): Flow<ApiResult<List<TopAlbum>>> {
        return mainRepository.getTopAlbumsBasedOnArtist(artistName)
    }
}
