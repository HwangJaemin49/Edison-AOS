package com.umc.edison.remote.datasources

import com.umc.edison.data.datasources.BubbleLocalDataSource
import com.umc.edison.data.datasources.BubbleRemoteDataSource
import com.umc.edison.data.model.BubbleEntity
import com.umc.edison.data.model.PositionedBubbleEntity
import com.umc.edison.remote.api.BubbleSpaceApiService
import com.umc.edison.remote.api.MyPageApiService
import com.umc.edison.remote.api.SyncApiService
import com.umc.edison.remote.model.space.GetBubblePositionResponse
import com.umc.edison.remote.model.mypage.GetDeletedBubbleListResponse
import com.umc.edison.remote.model.sync.toSyncBubbleRequest
import javax.inject.Inject

class BubbleRemoteDataSourceImpl @Inject constructor(
    private val bubbleSpaceApiService: BubbleSpaceApiService,
    private val myPageApiService: MyPageApiService,
    private val syncApiService: SyncApiService,
    private val bubbleLocalDataSource: BubbleLocalDataSource
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

    override suspend fun getBubblePosition(): List<PositionedBubbleEntity> {
//        val response = bubbleSpaceApiService.getBubblePosition().data
        val response = listOf(
            GetBubblePositionResponse(
                id = 1,
                x = -0.1f,
                y = -0.3f,
                group = 2
            ),
            GetBubblePositionResponse(
                id = 2,
                x = -0.5f,
                y = 0.3f,
                group = 2
            ),
            GetBubblePositionResponse(
                id = 3,
                x = 0.5f,
                y = -0.5f,
                group = 1
            ),
        )

        val result = mutableListOf<PositionedBubbleEntity>()

        response.map {
            val localBubble = bubbleLocalDataSource.getBubble(it.id)

            result.add(PositionedBubbleEntity(
                bubble = localBubble,
                x = it.x,
                y = it.y,
                group = it.group
            ))
        }

        return result
    }

    override suspend fun syncBubble(bubble: BubbleEntity): BubbleEntity {
        return syncApiService.syncBubble(bubble.toSyncBubbleRequest()).data.toData()
    }
}