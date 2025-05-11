package com.umc.edison.data.datasources

import com.umc.edison.data.model.label.LabelEntity

interface LabelRemoteDataSource {
    // CREATE
    suspend fun addLabel(label: LabelEntity): LabelEntity

    // UPDATE
    suspend fun updateLabel(label: LabelEntity): LabelEntity

    // DELETE
    suspend fun deleteLabel(label: LabelEntity): LabelEntity
}