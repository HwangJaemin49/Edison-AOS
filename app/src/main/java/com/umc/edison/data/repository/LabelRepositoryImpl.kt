package com.umc.edison.data.repository

import com.umc.edison.data.bound.flowDataResource
import com.umc.edison.data.datasources.LabelLocalDataSource
import com.umc.edison.data.model.toData
import com.umc.edison.domain.DataResource
import com.umc.edison.domain.model.Label
import com.umc.edison.domain.repository.LabelRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LabelRepositoryImpl @Inject constructor(
    private val labelLocalDataSource: LabelLocalDataSource,
) : LabelRepository {
    override fun getAllLabels(): Flow<DataResource<List<Label>>> = flowDataResource(
        dataAction = { labelLocalDataSource.getAllLabels() },
    )

    override fun addLabel(label: Label): Flow<DataResource<Unit>> = flowDataResource (
        dataAction = { labelLocalDataSource.addLabel(label.toData()) },
    )

    override fun updateLabel(label: Label): Flow<DataResource<Unit>> = flowDataResource (
        dataAction = { labelLocalDataSource.updateLabel(label.toData()) },
    )

    override fun deleteLabel(label: Label): Flow<DataResource<Unit>> = flowDataResource (
        dataAction = { labelLocalDataSource.softDeleteLabel(label.toData()) },
    )

    override fun getLabelDetail(labelId: Int): Flow<DataResource<Label>> = flowDataResource(
        dataAction = { labelLocalDataSource.getLabelDetail(labelId) },
    )

}