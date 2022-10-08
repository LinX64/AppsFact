/*
 * *
 *  * Created by Mohsen on 10/5/22, 2:43 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 10/4/22, 1:07 PM
 *
 */

package com.example.appsfactory.di.modules

import android.content.Context
import androidx.room.Room
import com.example.appsfactory.data.source.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "apps_factory_db"
        ).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun provideTopAlbumDao(appDb: AppDatabase) = appDb.topAlbumDao()

    @Singleton
    @Provides
    fun provideAlbumInfoDao(appDb: AppDatabase) = appDb.albumInfoDao()
}