/*
 * *
 *  * Created by Mohsen on 10/6/22, 12:15 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 10/6/22, 12:15 PM
 *
 */

package com.example.appsfactory

import com.example.appsfactory.data.repository.MainRepositoryImpl
import com.example.appsfactory.data.source.remote.ApiService
import com.example.appsfactory.domain.model.albumInfo.AlbumInfoResponse
import com.example.appsfactory.domain.model.artistList.SearchArtistResponse
import com.example.appsfactory.domain.model.top_albums.TopAlbumsResponse
import com.example.appsfactory.util.ApiState
import com.example.appsfactory.util.StubData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.mock
import kotlin.test.assertEquals

class MainRepositoryTest {

    private val getSearchArtistResponse = StubData.getJson("SearchArtistResponse.json")
    private val getTopAlbumResponse = StubData.getJson("TopAlbumsResponse.json")
    private val getAlbumInfoResponse = StubData.getJson("AlbumInfoResponse.json")

    private val artistName = "Justin Bieber"
    private val albumName = "Purpose (Deluxe)"

    @Test
    fun `WHEN Get Search Is Successful THEN Should check with Actual Name`(): Unit = runBlocking {
        //Given
        val mockResponse = StubData.mockGetSearchArtistWithJson(getSearchArtistResponse)
        val mockApiService = mockGetSearchArtistCallWithResponse(mockResponse)
        val repository = MainRepositoryImpl(mockApiService, Dispatchers.IO)

        //When
        val expectedJustin =
            mockResponse.results.artistmatches.artist.find { it.name == artistName }?.name

        repository.getArtist(artistName).collect {
            //Then
            if (it is ApiState.Success) {
                val actualName = it.data.artist.find { it.name == artistName }?.name
                assertEquals(actualName, expectedJustin)
            }
        }
    }

    @Test
    fun `WHEN Get Top Albums Is Successful THEN Should check with Actual Name`(): Unit =
        runBlocking {
            //Given
            val mockResponse = StubData.mockGetTopAlbumWithJson(getTopAlbumResponse)
            val mockApiService = mockGetTopAlbumsCallWithResponse(mockResponse)
            val repository = MainRepositoryImpl(mockApiService, Dispatchers.IO)

            //When
            val expected = mockResponse.topalbums.album.find { it.name == albumName }?.name

            repository.getTopAlbumsBasedOnArtist(artistName).collect {
                //Then
                if (it is ApiState.Success) {
                    val actualName = it.data.find { it.name == albumName }?.name
                    assertEquals(actualName, expected)
                }
            }
        }

    @Test
    fun `WHEN Get Album Info Is Successful THEN Should check with Actual Name`(): Unit =
        runBlocking {
            //Given
            val mockResponse = StubData.mockGetAlbumInfoWithJson(getAlbumInfoResponse)
            val mockApiService = mockGetAlbumInfoCallWithResponse(mockResponse)
            val repository = MainRepositoryImpl(mockApiService, Dispatchers.IO)

            //When
            val expected = mockResponse.album.name
            repository.getAlbumInfo(artistName, albumName).collect {
                //Then
                if (it is ApiState.Success) {
                    val actualName = it.data.name
                    assertEquals(actualName, expected)
                }
            }
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

    private fun mockGetAlbumInfoCallWithResponse(res: AlbumInfoResponse): ApiService =
        runBlocking {
            return@runBlocking mock<ApiService> {
                onBlocking { getAlbumInfo(artistName, albumName) }.thenReturn(res)
            }
        }
}