package com.umc.edison.data.sync

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.umc.edison.domain.usecase.sync.SyncLocalDataToServerUseCase

class SyncDataWorker(
    context: Context,
    workerParams: WorkerParameters,
    private val syncLocalDataToServerUseCase: SyncLocalDataToServerUseCase,
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        Log.i("SyncDataWorker", "Syncing data...")
        return try {
            syncLocalDataToServerUseCase()
            Log.i("SyncDataWorker", "Data synced successfully!")
            Result.success()
        } catch (e: Throwable) {
            Log.e("SyncDataWorker", "Failed to sync data", e)
            Result.retry()
        }
    }
}