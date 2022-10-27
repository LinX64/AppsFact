/*
 * *
 *  * Created by Mohsen on 10/27/22, 6:00 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 10/27/22, 6:00 PM
 *
 */

package com.example.appsfactory.presentation.top_albums

import com.example.appsfactory.domain.model.top_albums.TopAlbum

sealed interface TopAlbumsState {
    object Loading : TopAlbumsState
    data class Success(val data: List<TopAlbum>) : TopAlbumsState
    data class Error(val message: String) : TopAlbumsState
}