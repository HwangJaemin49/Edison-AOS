package com.umc.edison.data.datasources

import com.umc.edison.data.model.BubbleEntity
import com.umc.edison.data.model.LabelEntity

interface BubbleLocalDataSource {
    suspend fun getAllBubbles(): List<BubbleEntity>
    suspend fun addBubbles(bubbles: List<BubbleEntity>)
    suspend fun addBubble(bubble: BubbleEntity)

    suspend fun getBubblesByLabel(labelId: Int): List<BubbleEntity>
    suspend fun getBubble(bubbleId: Int): BubbleEntity

    suspend fun deleteBubbles(bubbles: List<BubbleEntity>)
    suspend fun deleteBubble(bubble: BubbleEntity)

    suspend fun updateBubbles(bubbles: List<BubbleEntity>)
    suspend fun updateBubble(bubble: BubbleEntity)

    suspend fun getUnSyncedBubbles(): List<BubbleEntity>
    suspend fun markAsSynced(bubble: BubbleEntity)

    suspend fun getBubbleDetail(bubbleId: Int): BubbleEntity
}