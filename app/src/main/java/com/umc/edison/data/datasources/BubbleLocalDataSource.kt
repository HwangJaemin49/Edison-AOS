package com.umc.edison.data.datasources

import com.umc.edison.data.model.BubbleEntity

interface BubbleLocalDataSource {
    suspend fun getAllBubbles(): List<BubbleEntity>
    suspend fun getStorageBubbles(): List<BubbleEntity>
    suspend fun getSearchBubbles(query: String): List<BubbleEntity>
    suspend fun addBubbles(bubbles: List<BubbleEntity>)
    suspend fun addBubble(bubble: BubbleEntity) : BubbleEntity

    suspend fun getBubblesByLabel(labelId: Int): List<BubbleEntity>
    suspend fun getBubble(bubbleId: Int): BubbleEntity

    suspend fun moveBubblesToTrash(bubbles: List<BubbleEntity>)
    suspend fun moveBubbleToTrash(bubble: BubbleEntity)

    suspend fun updateBubbles(bubbles: List<BubbleEntity>)
    suspend fun updateBubble(bubble: BubbleEntity) : BubbleEntity

    suspend fun syncBubbles(bubbles: List<BubbleEntity>)
    suspend fun getUnSyncedBubbles(): List<BubbleEntity>
    suspend fun markAsSynced(bubble: BubbleEntity)

    suspend fun getTrashedBubbles(): List<BubbleEntity>

    suspend fun recoverBubbles(bubbles: List<BubbleEntity>)
    suspend fun recoverBubble(bubble: BubbleEntity)

    suspend fun softDeleteBubbles(bubbles: List<BubbleEntity>)
    suspend fun softDeleteBubble(bubble: BubbleEntity)

    suspend fun deleteBubble(bubble: BubbleEntity)
    suspend fun addLinkedBubble(bubble: BubbleEntity)
}