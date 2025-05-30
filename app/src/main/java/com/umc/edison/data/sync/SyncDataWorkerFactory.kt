package com.umc.edison.data.sync

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.umc.edison.domain.usecase.sync.SyncLocalDataToServerUseCase
import javax.inject.Inject

class SyncDataWorkerFactory @Inject constructor(
    private val syncLocalDataToServerUseCase: SyncLocalDataToServerUseCase
) : WorkerFactory(){
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return when(workerClassName){
            SyncDataWorker::class.java.name -> SyncDataWorker(appContext, workerParameters, syncLocalDataToServerUseCase)
            else -> null
        }
    }

}