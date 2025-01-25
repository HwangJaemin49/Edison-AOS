package com.umc.edison.data.datasources

import com.umc.edison.data.model.LabelEntity

interface LabelLocalDataSource {
    suspend fun getAllLabels(): List<LabelEntity>
    suspend fun addLabels(labels: List<LabelEntity>)
    suspend fun addLabel(label: LabelEntity)
    suspend fun updateLabel(label: LabelEntity)
    suspend fun softDeleteLabel(label: LabelEntity)
    suspend fun deleteLabel(label: LabelEntity)

    suspend fun getUnSyncedLabels(): List<LabelEntity>
    suspend fun markAsSynced(label: LabelEntity)
}