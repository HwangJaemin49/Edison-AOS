package com.umc.edison.presentation.sync

import android.content.Context
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.umc.edison.data.sync.SyncDataWorker
import java.util.concurrent.TimeUnit

class SyncTrigger(private val context: Context) {

    private val networkStateMonitor = NetworkStateMonitor(context)

    fun setupSync() {
        networkStateMonitor.registerCallback {
            // 데이터 동기화 작업 등록
            scheduleSyncWork()
        }
    }

    fun triggerSync() {
        scheduleSyncWork()
    }

    private fun scheduleSyncWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .build()

        val syncWorkRequest = OneTimeWorkRequestBuilder<SyncDataWorker>()
            .setConstraints(constraints)
            .setInitialDelay(1, TimeUnit.MINUTES)
            .setBackoffCriteria(
                BackoffPolicy.LINEAR,
                PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS, TimeUnit.MILLISECONDS
            )
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            "DataSyncWork",
            ExistingWorkPolicy.KEEP,
            syncWorkRequest
        )
    }
}