/*
 * *
 *  * Created by Mohsen on 10/4/22, 3:18 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 10/4/22, 3:18 PM
 *
 */

package com.example.appsfactory.domain.usecase

import com.example.appsfactory.domain.repository.MainRepository
import javax.inject.Inject

class SearchArtistUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {
    fun getArtist(artistName: String) = mainRepository.getArtist(artistName)
}