/*
 * *
 *  * Created by Mohsen on 10/4/22, 3:18 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 10/4/22, 3:18 PM
 *
 */

package com.example.appsfactory.domain.usecase

import com.example.appsfactory.domain.model.artistList.Artist
import com.example.appsfactory.domain.repository.MainRepository
import com.example.appsfactory.util.ApiState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchArtistUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {

    operator fun invoke(artistName: String): Flow<ApiState<List<Artist>>> {
        return mainRepository.getArtist(artistName)
    }
}
