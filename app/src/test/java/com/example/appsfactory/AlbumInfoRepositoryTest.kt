/*
 * *
 *  * Created by Mohsen on 10/9/22, 3:57 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 10/9/22, 3:57 PM
 *
 */

package com.example.appsfactory

import com.example.appsfactory.data.repository.AlbumInfoRepositoryImpl
import com.example.appsfactory.data.source.local.AppDatabase
import com.example.appsfactory.data.source.remote.ApiService
import com.example.appsfactory.domain.model.albumInfo.AlbumInfoResponse
import com.example.appsfactory.util.StubData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.kotlin.mock
import javax.inject.Inject
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class AlbumInfoRepositoryTest {

    private val getAlbumInfoResponse = StubData.getJson("AlbumInfoResponse.json")

    private val artistName = "Justin Bieber"
    private val albumName = "Purpose (Deluxe)"
    private val id = 1

    @Inject
    lateinit var appDatabase: AppDatabase

    @Before
    fun setup() {
        appDatabase = mock()
    }

    @Test
    fun `WHEN Get Album Info Call Is Successful THEN Should check with Actual Name`() =
        runBlocking {
            //Given
            val mockResponse = StubData.mockGetAlbumInfoWithJson(getAlbumInfoResponse)
            val mockApiService = mockGetAlbumInfoCallWithResponse(mockResponse)
            val repository =
                AlbumInfoRepositoryImpl(mockApiService, appDatabase, Dispatchers.IO)

            //When
            val expected = mockResponse.album.name
            repository.getAlbumInfo(id, artistName, albumName).collect {
                if (it is ApiState.Success) {
                    val actual = it.data?.albumName

                    //Then
                    assertEquals(expected, actual)
                }
            }
        }

    private fun mockGetAlbumInfoCallWithResponse(res: AlbumInfoResponse): ApiService =
        runBlocking {
            return@runBlocking mock<ApiService> {
                onBlocking { fetchAlbumInfo(artistName, albumName) }.thenReturn(res)
            }
        }
}