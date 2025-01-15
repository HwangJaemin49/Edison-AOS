package com.umc.edison.data.datasources

import com.umc.edison.data.model.BubbleEntity

interface BubbleLocalDataSource {
    suspend fun getAllBubbles(): List<BubbleEntity>
    suspend fun getNotSyncedBubbles(): List<BubbleEntity>
    suspend fun updateSyncedBubbles(bubbles: List<BubbleEntity>)
}