/*
 * *
 *  * Created by Mohsen on 10/6/22, 2:34 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 10/6/22, 2:34 PM
 *
 */

package com.example.appsfactory.util

import com.example.appsfactory.domain.model.albumInfo.AlbumInfoResponse
import com.example.appsfactory.domain.model.artistList.SearchArtistResponse
import com.example.appsfactory.domain.model.top_albums.TopAlbumsResponse
import com.google.gson.Gson
import java.io.File

class StubData {
    companion object {
        fun getJson(path: String): String {
            val uri = ClassLoader.getSystemResource(path)
            return File(uri.path).readText()
        }

        fun mockGetSearchArtistWithJson(json: String): SearchArtistResponse {
            return Gson().fromJson(json, SearchArtistResponse::class.java)
        }

        fun mockGetTopAlbumWithJson(json: String): TopAlbumsResponse {
            return Gson().fromJson(json, TopAlbumsResponse::class.java)
        }

        fun mockGetAlbumInfoWithJson(json: String): AlbumInfoResponse {
            return Gson().fromJson(json, AlbumInfoResponse::class.java)
        }
    }
}