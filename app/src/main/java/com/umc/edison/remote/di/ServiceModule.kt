package com.umc.edison.remote.di

import com.umc.edison.remote.api.ArtLetterApiService
import com.umc.edison.remote.api.BubbleSpaceApiService
import com.umc.edison.remote.api.BubbleStorageApiService
import com.umc.edison.remote.api.MyPageApiService
import com.umc.edison.remote.api.RefreshTokenApiService
import com.umc.edison.remote.api.SyncApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object ServiceModule {
    @Provides
    @Singleton
    fun provideBubbleSpaceService(
        @NetworkModule.MainRetrofit retrofit: Retrofit
    ): BubbleSpaceApiService = retrofit.create(BubbleSpaceApiService::class.java)

    @Provides
    @Singleton
    fun provideBubbleStorageService(
        @NetworkModule.MainRetrofit retrofit: Retrofit
    ): BubbleStorageApiService = retrofit.create(BubbleStorageApiService::class.java)

    @Provides
    @Singleton
    fun provideSyncService(
        @NetworkModule.MainRetrofit retrofit: Retrofit
    ): SyncApiService = retrofit.create(SyncApiService::class.java)

    @Provides
    @Singleton
    fun provideMyPageService(
        @NetworkModule.MainRetrofit retrofit: Retrofit
    ): MyPageApiService = retrofit.create(MyPageApiService::class.java)

    @Provides
    @Singleton
    fun provideRefreshTokenService(
        @NetworkModule.RefreshRetrofit retrofit: Retrofit
    ): RefreshTokenApiService = retrofit.create(RefreshTokenApiService::class.java)

    @Provides
    @Singleton
    fun provideArtLetterService(
        @NetworkModule.MainRetrofit retrofit: Retrofit
    ): ArtLetterApiService = retrofit.create(ArtLetterApiService::class.java)
}