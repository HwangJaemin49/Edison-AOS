package com.umc.edison.remote.datasources

import com.umc.edison.data.datasources.LabelRemoteDataSource
import com.umc.edison.data.model.LabelEntity
import com.umc.edison.remote.api.BubbleSpaceApiService
import javax.inject.Inject

class LabelRemoteDataSourceImpl @Inject constructor(
    private val apiService: BubbleSpaceApiService
) : LabelRemoteDataSource{
    override suspend fun getAllLabels(): List<LabelEntity> {
        return apiService.getAllLabels().data.map { it.toData() }
    }

    override suspend fun syncLabel(label: LabelEntity) {
        TODO("Not yet implemented")
    }
}