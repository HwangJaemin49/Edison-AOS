package com.umc.edison.data.sync

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.umc.edison.domain.usecase.sync.SyncDataUseCase

class SyncDataWorker(
    context: Context,
    workerParams: WorkerParameters,
    private val syncDataUseCase: SyncDataUseCase,
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        Log.i("SyncDataWorker", "Syncing data...")
        return try {
            syncDataUseCase()
            Log.i("SyncDataWorker", "Data synced successfully!")
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}