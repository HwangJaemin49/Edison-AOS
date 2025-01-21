package com.umc.edison

import android.app.Application
import androidx.work.Configuration
import com.umc.edison.data.di.EntryPointModule
import com.umc.edison.data.sync.SyncDataWorkerFactory
import com.umc.edison.presentation.sync.SyncTrigger
import dagger.hilt.EntryPoints
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class EdisonApplication : Application(), Configuration.Provider {

    override fun onCreate() {
        super.onCreate()

        // NetworkStateMonitor 초기화 및 데이터 동기화 설정
        val syncTrigger = SyncTrigger(this)
        syncTrigger.setupSync()
    }

    override val workManagerConfiguration: Configuration
        get() {
            val syncDataWorkerFactory: SyncDataWorkerFactory = EntryPoints.get(
                applicationContext,
                EntryPointModule::class.java
            ).getSyncDataWorkerFactory()

            return Configuration.Builder()
                .setMinimumLoggingLevel(android.util.Log.DEBUG)
                .setWorkerFactory(syncDataWorkerFactory)
                .build()
        }
}