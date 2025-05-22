package com.umc.edison.remote.datasources

import com.umc.edison.data.datasources.LabelLocalDataSource
import com.umc.edison.data.datasources.LabelRemoteDataSource
import com.umc.edison.data.model.label.LabelEntity
import com.umc.edison.remote.api.SyncApiService
import com.umc.edison.remote.model.sync.toSyncLabelRequest
import javax.inject.Inject

class LabelRemoteDataSourceImpl @Inject constructor(
) : LabelRemoteDataSource{
    override suspend fun addLabel(label: LabelEntity): LabelEntity {
        TODO("Not yet implemented")
    }

    override suspend fun updateLabel(label: LabelEntity): LabelEntity {
        TODO("Not yet implemented")
    }

    override suspend fun deleteLabel(id: String): LabelEntity {
        TODO("Not yet implemented")
    }

}