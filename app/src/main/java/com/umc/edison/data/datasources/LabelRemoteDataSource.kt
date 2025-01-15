package com.umc.edison.data.datasources

import com.umc.edison.data.model.LabelEntity

interface LabelRemoteDataSource {
    suspend fun getLabels(): List<LabelEntity>
    suspend fun syncLabels(labels: List<LabelEntity>)
}