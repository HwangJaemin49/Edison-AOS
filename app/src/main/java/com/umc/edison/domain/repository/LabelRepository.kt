package com.umc.edison.domain.repository

import com.umc.edison.domain.DataResource
import com.umc.edison.domain.model.Label
import kotlinx.coroutines.flow.Flow

interface LabelRepository {
    fun getAllLabels(): Flow<DataResource<List<Label>>>
    fun addLabel(label: Label): Flow<DataResource<Unit>>
    fun updateLabel(label: Label): Flow<DataResource<Unit>>
    fun deleteLabel(label: Label): Flow<DataResource<Unit>>
    fun getLabelDetail(labelId: Int): Flow<DataResource<Label>>
}