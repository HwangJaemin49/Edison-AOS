package com.umc.edison.data.datasources

import com.umc.edison.data.model.BubbleEntity

interface BubbleLocalDataSource {
    suspend fun getAllBubbles(): List<BubbleEntity>
    suspend fun addBubbles(bubbles: List<BubbleEntity>)
    suspend fun addBubble(bubble: BubbleEntity)

    suspend fun getUnSyncedBubbles(): List<BubbleEntity>
    suspend fun markAsSynced(bubble: BubbleEntity)
}