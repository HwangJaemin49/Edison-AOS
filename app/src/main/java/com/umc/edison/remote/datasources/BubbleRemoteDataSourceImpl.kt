package com.umc.edison.remote.datasources

import com.umc.edison.data.datasources.BubbleRemoteDataSource
import com.umc.edison.data.model.BubbleEntity
import com.umc.edison.remote.api.BubbleSpaceApiService
import javax.inject.Inject

class BubbleRemoteDataSourceImpl @Inject constructor(
    private val bubbleSpaceApiService: BubbleSpaceApiService
) : BubbleRemoteDataSource {
    override suspend fun getAllBubbles(): List<BubbleEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun syncBubble(bubble: BubbleEntity) {
        TODO("Not yet implemented")
    }
}