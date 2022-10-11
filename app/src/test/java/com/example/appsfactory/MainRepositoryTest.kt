/*
 * *
 *  * Created by Mohsen on 10/6/22, 12:15 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 10/6/22, 12:15 PM
 *
 */

package com.example.appsfactory

import com.example.appsfactory.data.repository.MainRepositoryImpl
import com.example.appsfactory.data.source.local.AppDatabase
import com.example.appsfactory.data.source.local.dao.TopAlbumsDao
import com.example.appsfactory.data.source.local.entity.AlbumEntity
import com.example.appsfactory.data.source.remote.ApiService
import com.example.appsfactory.domain.model.artistList.SearchArtistResponse
import com.example.appsfactory.domain.model.top_albums.TopAlbumsResponse
import com.example.appsfactory.util.ApiState
import com.example.appsfactory.util.StubData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.kotlin.mock
import javax.inject.Inject
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class MainRepositoryTest {

    private val getSearchArtistResponse = StubData.getJson("SearchArtistResponse.json")
    private val getTopAlbumResponse = StubData.getJson("TopAlbumsResponse.json")

    private val artistName = "Justin Bieber"
    private val albumName = "Purpose (Deluxe)"

    @Inject
    lateinit var db: AppDatabase

    @Inject
    lateinit var albumsDao: TopAlbumsDao

    @Before
    fun setup() {
        db = mock()
        albumsDao = mock()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `WHEN Get Search Call Is Successful THEN Should check with Actual Name`() = runTest {
        //Given
        val mockResponse = StubData.mockGetSearchArtistWithJson(getSearchArtistResponse)
        val mockApiService = mockGetSearchArtistCallWithResponse(mockResponse)
        val repository = MainRepositoryImpl(mockApiService, db, Dispatchers.IO)

        insertDummyData()

        //When
        repository.getArtist(artistName).first().let {
            if (it is ApiState.Success) {
                val artistName = it.data?.artist?.get(0)?.name
                assertEquals(artistName, artistName)
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `WHEN Get Top Albums Call Is Successful THEN Should check with Actual Name From Database`() =
        runTest {
            //Given

            val mockResponse = StubData.mockGetTopAlbumWithJson(getTopAlbumResponse)
            val mockApiService = mockGetTopAlbumsCallWithResponse(mockResponse)
            val repository = MainRepositoryImpl(mockApiService, db, Dispatchers.IO)

            //When
            val expected = mockResponse.topalbums.album.find { it.name == albumName }?.name
            repository.getTopAlbumsBasedOnArtist(artistName).collect { apiState ->
                //Then
                if (apiState is ApiState.Success) {
                    val actualName = apiState.data?.find { it.name == albumName }?.name
                    assertEquals(actualName, expected)
                }
            }
        }

    private suspend fun insertDummyData() {
        val albums = List(1) {
            AlbumEntity(
                0,
                name = albumName,
                artist = artistName,
                image = "https://lastfm.freetls.fastly.net/i/u/34s/2a96cbd8b46e442fc41c2be3b5b7e943.png",
                isBookmarked = 1
            )
        }
        albumsDao.insertAll(albums)
    }

    private fun mockGetSearchArtistCallWithResponse(res: SearchArtistResponse): ApiService =
        runBlocking {
            return@runBlocking mock<ApiService> {
                onBlocking { getArtist(artistName) }.thenReturn(res)
            }
        }

    private fun mockGetTopAlbumsCallWithResponse(res: TopAlbumsResponse): ApiService =
        runBlocking {
            return@runBlocking mock<ApiService> {
                onBlocking { getTopAlbumsBasedOnArtist(artistName) }.thenReturn(res)
            }
        }
}