/*
 * *
 *  * Created by Mohsen on 10/4/22, 1:34 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 10/4/22, 1:34 PM
 *
 */

package com.example.appsfactory.di.modules

import com.example.appsfactory.data.repository.AlbumInfoRepositoryImpl
import com.example.appsfactory.data.repository.AlbumRepositoryImpl
import com.example.appsfactory.data.repository.MainRepositoryImpl
import com.example.appsfactory.domain.repository.AlbumInfoRepository
import com.example.appsfactory.domain.repository.AlbumRepository
import com.example.appsfactory.domain.repository.MainRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepoModule {

    @Binds
    fun bindsMainRepository(
        mainRepository: MainRepositoryImpl
    ): MainRepository

    @Binds
    fun bindsAlbumRepository(
        albumRepository: AlbumRepositoryImpl
    ): AlbumRepository

    @Binds
    fun bindsAlbumInfoRepository(
        albumInfoRepository: AlbumInfoRepositoryImpl
    ): AlbumInfoRepository
}