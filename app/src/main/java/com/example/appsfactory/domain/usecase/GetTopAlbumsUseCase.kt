/*
 * *
 *  * Created by Mohsen on 10/4/22, 2:05 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 10/4/22, 2:05 PM
 *
 */

package com.example.appsfactory.domain.usecase

import com.example.appsfactory.domain.repository.MainRepository
import javax.inject.Inject

class GetTopAlbumsUseCase @Inject constructor(private val mainRepository: MainRepository) {

    suspend operator fun invoke(artistName: String) =
        mainRepository.getTopAlbumsBasedOnArtist(artistName)
}
