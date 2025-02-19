package com.umc.edison.remote.datasources

import com.umc.edison.data.datasources.BubbleLocalDataSource
import com.umc.edison.data.datasources.BubbleRemoteDataSource
import com.umc.edison.data.model.BubbleEntity
import com.umc.edison.data.model.PositionedBubbleEntity
import com.umc.edison.remote.api.BubbleSpaceApiService
import com.umc.edison.remote.api.SyncApiService
import com.umc.edison.remote.model.sync.toSyncBubbleRequest
import javax.inject.Inject

class BubbleRemoteDataSourceImpl @Inject constructor(
    private val bubbleSpaceApiService: BubbleSpaceApiService,
    private val syncApiService: SyncApiService,
    private val bubbleLocalDataSource: BubbleLocalDataSource
) : BubbleRemoteDataSource {
    override suspend fun getBubblePosition(): List<PositionedBubbleEntity> {
        val response = bubbleSpaceApiService.getBubblePosition().data

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

    override suspend fun syncBubbleToLocal() {
        val remoteBubbles = syncApiService.getAllBubbles().data.map { it.toData() }

        bubbleLocalDataSource.syncBubbles(remoteBubbles)

        remoteBubbles.forEach {
            bubbleLocalDataSource.addLinkedBubble(it)
        }
    }
}