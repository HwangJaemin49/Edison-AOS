package com.umc.edison.remote.datasources

import com.umc.edison.data.datasources.LabelRemoteDataSource
import com.umc.edison.data.model.LabelEntity
import com.umc.edison.remote.api.BubbleSpaceApiService
import com.umc.edison.remote.api.SyncApiService
import com.umc.edison.remote.model.toAddLabelLocal
import javax.inject.Inject

class LabelRemoteDataSourceImpl @Inject constructor(
    private val bubbleSpaceApiService: BubbleSpaceApiService,
    private val syncApiService: SyncApiService,
) : LabelRemoteDataSource{
    override suspend fun getAllLabels(): List<LabelEntity> {
        return bubbleSpaceApiService.getAllLabels().data.map { it.toData() }
    }

    override suspend fun getLabelDetail(labelId: Int): LabelEntity {
        return bubbleSpaceApiService.getLabelDetail(labelId).data.toData()
    }

    override suspend fun syncLabel(label: LabelEntity) {
        // 임시로 라벨 생성으로 구현해 둠
        syncApiService.addLabel(label.toAddLabelLocal())
    }
}