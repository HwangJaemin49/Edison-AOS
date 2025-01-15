package com.umc.edison.remote.di

import com.umc.edison.data.datasources.BubbleRemoteDataSource
import com.umc.edison.data.datasources.LabelRemoteDataSource
import com.umc.edison.remote.datasources.BubbleRemoteDataSourceImpl
import com.umc.edison.remote.datasources.LabelRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RemoteDataSourceModule {
    @Binds
    @Singleton
    abstract fun bindBubbleRemoteDataSource(
        bubbleRemoteDataSourceImpl: BubbleRemoteDataSourceImpl
    ): BubbleRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindLabelRemoteDataSource(
        labelRemoteDataSourceImpl: LabelRemoteDataSourceImpl
    ): LabelRemoteDataSource
}