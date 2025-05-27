package com.umc.edison

import android.app.Application
import android.util.Log
import androidx.work.Configuration
import com.umc.edison.data.di.EntryPointModule
import com.umc.edison.data.sync.SyncDataWorkerFactory
import com.umc.edison.presentation.sync.SyncTrigger
import dagger.hilt.EntryPoints
import dagger.hilt.android.HiltAndroidApp
import io.branch.referral.Branch

@HiltAndroidApp
class EdisonApplication : Application(), Configuration.Provider {

    override fun onCreate() {
        super.onCreate()

        // Branch SDK 초기화
        Branch.getAutoInstance(this)

        // 기존 네트워크 상태 모니터링 초기화
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
                .setMinimumLoggingLevel(Log.DEBUG)
                .setWorkerFactory(syncDataWorkerFactory)
                .build()
        }
}
