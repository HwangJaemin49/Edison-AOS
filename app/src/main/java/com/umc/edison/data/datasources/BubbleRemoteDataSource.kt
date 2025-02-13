package com.umc.edison.data.datasources

import com.umc.edison.data.model.BubbleEntity
import com.umc.edison.data.model.PositionedBubbleEntity
import com.umc.edison.data.model.PositionedGroupEntity

interface BubbleRemoteDataSource {
    suspend fun getAllBubbles(): List<BubbleEntity>
    suspend fun getTrashedBubbles(): List<BubbleEntity>

    suspend fun getBubblePosition(): List<PositionedBubbleEntity>
    suspend fun getBubbleGroupPosition(): List<PositionedGroupEntity>

    suspend fun syncBubble(bubble: BubbleEntity): BubbleEntity
}