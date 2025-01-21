package com.umc.edison.data.di

import com.umc.edison.data.sync.SyncDataWorkerFactory
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@EntryPoint
interface EntryPointModule {
    fun getSyncDataWorkerFactory(): SyncDataWorkerFactory
}