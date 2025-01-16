package com.umc.edison.data.datasources

import com.umc.edison.data.model.LabelEntity

interface LabelLocalDataSource {
    suspend fun getAllLabels(): List<LabelEntity>
    suspend fun getNotSyncedLabels(): List<LabelEntity>
    suspend fun addLabel(label: LabelEntity)
    suspend fun updateSyncedLabels(labels: List<LabelEntity>)
}