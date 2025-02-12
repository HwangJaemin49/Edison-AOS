package com.umc.edison.data.datasources

import com.umc.edison.data.model.BubbleEntity

interface BubbleRemoteDataSource {
    suspend fun getAllBubbles(): List<BubbleEntity>
    suspend fun getStorageBubbles(): List<BubbleEntity>
    suspend fun getTrashedBubbles(): List<BubbleEntity>

    suspend fun syncBubble(bubble: BubbleEntity): BubbleEntity
}