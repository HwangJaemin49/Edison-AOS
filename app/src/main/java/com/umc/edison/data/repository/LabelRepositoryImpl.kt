package com.umc.edison.data.repository

import com.umc.edison.data.bound.flowDataResource
import com.umc.edison.data.datasources.LabelLocalDataSource
import com.umc.edison.data.datasources.LabelRemoteDataSource
import com.umc.edison.data.model.toEntity
import com.umc.edison.domain.DataResource
import com.umc.edison.domain.model.Label
import com.umc.edison.domain.repository.LabelRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LabelRepositoryImpl @Inject constructor(
    private val labelLocalDataSource: LabelLocalDataSource,
    private val labelRemoteDataSource: LabelRemoteDataSource
) : LabelRepository {
    override fun getAllLabels(): Flow<DataResource<List<Label>>> = flowDataResource(
        remoteDataAction = { labelRemoteDataSource.getAllLabels() },
        localDataAction = { labelLocalDataSource.getAllLabels() },
        saveCacheAction = { labelLocalDataSource.addLabels(it) },
    )

    override fun addLabel(label: Label): Flow<DataResource<Unit>> = flowDataResource (
        dataAction = { labelLocalDataSource.addLabel(label.toEntity()) },
    )

    override fun updateLabel(label: Label): Flow<DataResource<Unit>> = flowDataResource (
        dataAction = { labelLocalDataSource.updateLabel(label.toEntity()) },
    )

    override fun deleteLabel(label: Label): Flow<DataResource<Unit>> = flowDataResource (
        dataAction = { labelLocalDataSource.softDeleteLabel(label.toEntity()) },
    )

    override fun getLabelDetail(labelId: Int): Flow<DataResource<Label>> = flowDataResource(
        remoteDataAction = { labelRemoteDataSource.getLabelDetail(labelId) },
        localDataAction = { labelLocalDataSource.getLabelDetail(labelId) },
        saveCacheAction = { labelLocalDataSource.addLabel(it) },
    )

}