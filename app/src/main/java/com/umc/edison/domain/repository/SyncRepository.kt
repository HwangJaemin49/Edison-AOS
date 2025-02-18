package com.umc.edison.domain.repository

interface SyncRepository {
    suspend fun syncLabelData()
    suspend fun syncBubbleData()
    suspend fun syncServerDataToLocal()
}