package com.umc.edison.domain.repository

interface SyncRepository {
    suspend fun syncLocalDataToServer()
    suspend fun syncServerDataToLocal()
}