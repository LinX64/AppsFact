/*
 * *
 *  * Created by Mohsen on 10/4/22, 1:34 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 10/4/22, 1:34 PM
 *
 */

package com.example.appsfactory.di.modules

import androidx.viewbinding.BuildConfig
import com.example.appsfactory.data.repository.AlbumRepositoryImpl
import com.example.appsfactory.data.repository.MainRepositoryImpl
import com.example.appsfactory.data.source.local.AppDatabase
import com.example.appsfactory.data.source.local.dao.TopAlbumsDao
import com.example.appsfactory.data.source.remote.ApiService
import com.example.appsfactory.domain.repository.AlbumRepository
import com.example.appsfactory.domain.repository.MainRepository
import com.example.appsfactory.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideBaseUrl() = Constants.BASE_URL

    @Provides
    @Singleton
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    } else OkHttpClient
        .Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(
        gsonConverterFactory: GsonConverterFactory,
        okHttpClient: OkHttpClient,
        baseUrl: String
    ): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(gsonConverterFactory)
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideMoshiConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideMainRepository(
        apiService: ApiService,
        appDatabase: AppDatabase
    ): MainRepository = MainRepositoryImpl(apiService, appDatabase)

    @Singleton
    @Provides
    fun provideAlbumRepository(
        albumsDao: TopAlbumsDao
    ): AlbumRepository = AlbumRepositoryImpl(albumsDao)

    @IoDispatcher
    @Provides
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @MainDispatcher
    @Provides
    fun providesMainDispatcher(): CoroutineDispatcher = Dispatchers.Main
}

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class IoDispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class MainDispatcher