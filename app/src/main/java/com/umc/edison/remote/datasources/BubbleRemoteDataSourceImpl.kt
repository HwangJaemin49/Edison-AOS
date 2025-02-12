package com.umc.edison.remote.datasources

import com.umc.edison.data.datasources.BubbleRemoteDataSource
import com.umc.edison.data.model.BubbleEntity
import com.umc.edison.remote.api.BubbleSpaceApiService
import com.umc.edison.remote.api.MyPageApiService
import com.umc.edison.remote.api.SyncApiService
import com.umc.edison.remote.model.mypage.GetDeletedBubbleListResponse
import com.umc.edison.remote.model.sync.toSyncBubbleRequest
import javax.inject.Inject

class BubbleRemoteDataSourceImpl @Inject constructor(
    private val bubbleSpaceApiService: BubbleSpaceApiService,
    private val myPageApiService: MyPageApiService,
    private val syncApiService: SyncApiService,
) : BubbleRemoteDataSource {
    override suspend fun getAllBubbles(): List<BubbleEntity> {
        val response = bubbleSpaceApiService.getAllBubbles().data

        return response.map { it.toData() }
    }

    override suspend fun getTrashedBubbles(): List<BubbleEntity> {
        val response: List<GetDeletedBubbleListResponse>
            = myPageApiService.getDeletedBubbles().data

        return response.map { it.toData() }
    }

    override suspend fun syncBubble(bubble: BubbleEntity): BubbleEntity {
        return syncApiService.syncBubble(bubble.toSyncBubbleRequest()).data.toData()
    }
}