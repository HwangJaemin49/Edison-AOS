package com.umc.edison.data.di

import com.umc.edison.data.repository.BubbleRepositoryImpl
import com.umc.edison.data.repository.LabelRepositoryImpl
import com.umc.edison.data.repository.SyncRepositoryImpl
import com.umc.edison.data.repository.UserRepositoryImpl
import com.umc.edison.domain.repository.BubbleRepository
import com.umc.edison.domain.repository.LabelRepository
import com.umc.edison.domain.repository.SyncRepository
import com.umc.edison.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindBubbleRepository(
        bubbleRepositoryImpl: BubbleRepositoryImpl
    ): BubbleRepository

    @Binds
    @Singleton
    abstract fun bindLabelRepository(
        labelRepositoryImpl: LabelRepositoryImpl
    ): LabelRepository

    @Binds
    @Singleton
    abstract fun bindSyncRepository(
        syncRepositoryImpl: SyncRepositoryImpl
    ): SyncRepository

    @Binds
    @Singleton
    abstract fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository
}