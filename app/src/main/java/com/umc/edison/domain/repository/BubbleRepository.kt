package com.umc.edison.domain.repository

import com.umc.edison.domain.DataResource
import com.umc.edison.domain.model.Bubble
import com.umc.edison.domain.model.ClusteredBubbles
import kotlinx.coroutines.flow.Flow

interface BubbleRepository {
    fun getAllBubbles(): Flow<DataResource<List<Bubble>>>
    fun addBubbles(bubbles: List<Bubble>): Flow<DataResource<Unit>>
    fun softDeleteBubbles(bubbles: List<Bubble>): Flow<DataResource<Unit>>
    fun updateBubble(bubble: Bubble): Flow<DataResource<Bubble>>
    fun updateBubbles(bubbles: List<Bubble>): Flow<DataResource<Unit>>
    fun getBubble(bubbleId: Int): Flow<DataResource<Bubble>>

    fun getTrashedBubbles(): Flow<DataResource<List<Bubble>>>
    fun recoverBubbles(bubbles: List<Bubble>): Flow<DataResource<Unit>>

    fun deleteBubbles(bubbles: List<Bubble>): Flow<DataResource<Unit>>
    fun addBubble(bubble: Bubble): Flow<DataResource<Bubble>>

    fun getClusteredBubbles(): Flow<DataResource<List<ClusteredBubbles>>>
}