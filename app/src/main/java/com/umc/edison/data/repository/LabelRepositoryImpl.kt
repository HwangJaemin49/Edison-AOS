package com.umc.edison.data.repository

import com.umc.edison.data.bound.flowDataResource
import com.umc.edison.data.bound.flowSyncDataResource
import com.umc.edison.data.datasources.LabelLocalDataSource
import com.umc.edison.data.datasources.LabelRemoteDataSource
import com.umc.edison.data.model.label.toData
import com.umc.edison.domain.DataResource
import com.umc.edison.domain.model.label.Label
import com.umc.edison.domain.repository.LabelRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LabelRepositoryImpl @Inject constructor(
    private val labelLocalDataSource: LabelLocalDataSource,
    private val labelRemoteDataSource: LabelRemoteDataSource
) : LabelRepository {
    // CREATE
    override fun addLabel(label: Label): Flow<DataResource<Unit>> =
        flowSyncDataResource(
            localAction = { labelLocalDataSource.addLabel(label.toData()) },
            remoteSync = { labelRemoteDataSource.addLabel(label.toData()) },
            onRemoteSuccess = { remoteData -> labelLocalDataSource.markAsSynced(remoteData) }
        )

    // READ
    override fun getAllLabels(): Flow<DataResource<List<Label>>> =
        flowDataResource(
            dataAction = { labelLocalDataSource.getAllLabels() }
        )

    override fun getLabel(id: String): Flow<DataResource<Label>> =
        flowDataResource(
            dataAction = { labelLocalDataSource.getLabel(id) }
        )

    // UPDATE
    override fun updateLabel(label: Label): Flow<DataResource<Unit>> =
        flowSyncDataResource(
            localAction = { labelLocalDataSource.updateLabel(label.toData()) },
            remoteSync = { labelRemoteDataSource.updateLabel(label.toData()) },
            onRemoteSuccess = { remoteData -> labelLocalDataSource.markAsSynced(remoteData) }
        )

    // DELETE
    override fun deleteLabel(id: String): Flow<DataResource<Unit>> =
        flowSyncDataResource(
            localAction = { labelLocalDataSource.softDeleteLabel(labelLocalDataSource.getLabel(id)) },
            remoteSync = { labelRemoteDataSource.deleteLabel(labelLocalDataSource.getLabel(id)) },
            onRemoteSuccess = { remoteData -> labelLocalDataSource.deleteLabel(remoteData) }
        )
}