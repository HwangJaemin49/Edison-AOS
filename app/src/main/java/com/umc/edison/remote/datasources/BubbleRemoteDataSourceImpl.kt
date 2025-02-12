package com.umc.edison.remote.datasources

import com.umc.edison.data.datasources.BubbleRemoteDataSource
import com.umc.edison.data.model.BubbleEntity
import com.umc.edison.remote.api.BubbleSpaceApiService
import com.umc.edison.remote.api.BubbleStorageApiService
import com.umc.edison.remote.api.MyPageApiService
import com.umc.edison.remote.model.mypage.GetDeletedBubbleListResponse
import javax.inject.Inject

class BubbleRemoteDataSourceImpl @Inject constructor(
    private val bubbleSpaceApiService: BubbleSpaceApiService,
    private val bubbleStorageApiService: BubbleStorageApiService,
    private val myPageApiService: MyPageApiService,
) : BubbleRemoteDataSource {
    override suspend fun getAllBubbles(): List<BubbleEntity> {
        val response = bubbleSpaceApiService.getAllBubbles().data

        return response.map { it.toData() }
    }

    override suspend fun getStorageBubbles(): List<BubbleEntity> {
        val response = bubbleStorageApiService.getStorageBubbles().data

        return response.map { it.toData() }
    }

    override suspend fun getTrashedBubbles(): List<BubbleEntity> {
        val response: List<GetDeletedBubbleListResponse>
            = myPageApiService.getDeletedBubbles().data

        return response.map { it.toData() }
    }

    override suspend fun syncBubble(bubble: BubbleEntity): BubbleEntity {
        TODO("Not yet implemented")
    }
}