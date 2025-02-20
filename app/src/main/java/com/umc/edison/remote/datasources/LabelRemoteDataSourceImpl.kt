package com.umc.edison.remote.datasources

import com.umc.edison.data.datasources.LabelLocalDataSource
import com.umc.edison.data.datasources.LabelRemoteDataSource
import com.umc.edison.data.model.LabelEntity
import com.umc.edison.remote.api.SyncApiService
import com.umc.edison.remote.model.sync.toSyncLabelRequest
import javax.inject.Inject

class LabelRemoteDataSourceImpl @Inject constructor(
    private val syncApiService: SyncApiService,
    private val labelLocalDataSource: LabelLocalDataSource,
) : LabelRemoteDataSource{
    override suspend fun syncLabel(label: LabelEntity): LabelEntity {
        return syncApiService.syncLabel(label.toSyncLabelRequest()).data.toData()
    }

    override suspend fun syncLabelToLocal() {
        val remoteLabels = syncApiService.getAllLabels()
        val convertedLabels = remoteLabels.data.map { it.toData() }

        labelLocalDataSource.syncLabels(convertedLabels)
    }
}