/*
 * *
 *  * Created by Mohsen on 10/6/22, 12:15 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 10/6/22, 12:15 PM
 *
 */

package com.example.appsfactory

import com.example.appsfactory.data.repository.MainRepositoryImpl
import com.example.appsfactory.data.source.local.dao.TopAlbumsDao
import com.example.appsfactory.data.source.local.entity.TopAlbumEntity
import com.example.appsfactory.data.source.remote.ApiService
import com.example.appsfactory.domain.model.artistList.SearchArtistResponse
import com.example.appsfactory.domain.model.top_albums.TopAlbumsResponse
import com.example.appsfactory.util.ApiState
import com.example.appsfactory.util.StubData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
    lateinit var albumsDao: TopAlbumsDao

    @Before
    fun setup() {
        albumsDao = mock()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `WHEN Get SearchArtist Call Is Successful THEN Should check with Actual Name`() = runTest {
        //Given
        val mockResponse = StubData.mockGetSearchArtistWithJson(getSearchArtistResponse)
        val mockApiService = mockGetSearchArtistCallWithResponse(mockResponse)
        val repository = MainRepositoryImpl(mockApiService, Dispatchers.IO)

        insertDummyData()

        //When
        repository.getArtist(artistName).collect {
            if (it is ApiState.Success) {
                //Then
                val expected = it.data[0].name
                assertEquals(expected, artistName)
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
            val repository = MainRepositoryImpl(mockApiService, Dispatchers.IO)

            //When
            repository.getTopAlbumsBasedOnArtist(artistName).collect { apiState ->
                //Then
                val expectedName = apiState.find { it.name == albumName }?.name
                assertEquals(expectedName, albumName)
            }
        }

    private suspend fun insertDummyData() {
        val album = TopAlbumEntity(
            0,
            name = albumName,
            artist = artistName,
            image = "https://lastfm.freetls.fastly.net/i/u/34s/2a96cbd8b46e442fc41c2be3b5b7e943.png",
            isBookmarked = 1
        )
        albumsDao.insert(album)
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