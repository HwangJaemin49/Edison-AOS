package com.umc.edison.data.repository

import com.umc.edison.data.bound.flowDataResource
import com.umc.edison.data.datasources.LabelLocalDataSource
import com.umc.edison.data.datasources.LabelRemoteDataSource
import com.umc.edison.data.model.LabelEntity
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
        localDataAction = { labelLocalDataSource.getAllLabels() },
        getNotSyncedAction = { labelLocalDataSource.getNotSyncedLabels() },
        syncRemoteAction = { labels -> labelRemoteDataSource.syncLabels(labels as List<LabelEntity>) },
        updateSyncedAction = { labels -> labelLocalDataSource.updateSyncedLabels(labels as List<LabelEntity>) }
    )

    override fun addLabel(label: Label): Flow<DataResource<Unit>> = flowDataResource (
        localDataAction = { labelLocalDataSource.addLabel(label.toEntity()) },
        getNotSyncedAction = { labelLocalDataSource.getNotSyncedLabels() },
        syncRemoteAction = { labels -> labelRemoteDataSource.syncLabels(labels as List<LabelEntity>) },
        updateSyncedAction = { labels -> labelLocalDataSource.updateSyncedLabels(labels as List<LabelEntity>) }
    )

    override fun updateLabel(label: Label): Flow<DataResource<Unit>> = flowDataResource (
        localDataAction = { labelLocalDataSource.updateLabel(label.toEntity()) },
        getNotSyncedAction = { labelLocalDataSource.getNotSyncedLabels() },
        syncRemoteAction = { labels -> labelRemoteDataSource.syncLabels(labels as List<LabelEntity>) },
        updateSyncedAction = { labels -> labelLocalDataSource.updateSyncedLabels(labels as List<LabelEntity>) }
    )

}