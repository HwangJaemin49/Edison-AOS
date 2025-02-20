package com.umc.edison.data.datasources

import com.umc.edison.data.model.LabelEntity

interface LabelRemoteDataSource {
    suspend fun syncLabel(label: LabelEntity): LabelEntity
    suspend fun syncLabelToLocal()
}