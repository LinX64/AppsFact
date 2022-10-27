/*
 * *
 *  * Created by Mohsen on 10/27/22, 6:00 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 10/27/22, 6:00 PM
 *
 */

package com.example.appsfactory.ui.search

import com.example.appsfactory.domain.model.artistList.Artist

sealed interface ArtistListState {
    object Loading : ArtistListState
    data class Success(val artists: List<Artist>) : ArtistListState
    data class Error(val message: String) : ArtistListState
}