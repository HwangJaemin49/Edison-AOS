package com.umc.edison.local.di

import com.umc.edison.data.datasources.BubbleLocalDataSource
import com.umc.edison.data.datasources.LabelLocalDataSource
import com.umc.edison.data.datasources.PrefDataSource
import com.umc.edison.local.datasources.BubbleLocalDataSourceImpl
import com.umc.edison.local.datasources.LabelLocalDataSourceImpl
import com.umc.edison.local.datasources.PrefDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class LocalDataSourceModule {
    @Binds
    @Singleton
    abstract fun bindBubbleLocalDataSource(
        bubbleLocalDataSourceImpl: BubbleLocalDataSourceImpl
    ): BubbleLocalDataSource

    @Binds
    @Singleton
    abstract fun bindLabelLocalDataSource(
        labelLocalDataSourceImpl: LabelLocalDataSourceImpl
    ): LabelLocalDataSource

    @Binds
    @Singleton
    abstract fun bindPrefDataSource(
        prefDataSourceImpl: PrefDataSourceImpl
    ): PrefDataSource
}