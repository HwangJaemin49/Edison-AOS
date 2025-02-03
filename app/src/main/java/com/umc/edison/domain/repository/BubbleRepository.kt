package com.umc.edison.domain.repository

import com.umc.edison.domain.DataResource
import com.umc.edison.domain.model.Bubble
import com.umc.edison.domain.model.Label
import kotlinx.coroutines.flow.Flow

interface BubbleRepository {
    fun getAllBubbles(): Flow<DataResource<List<Bubble>>>
    fun addBubbles(bubbles: List<Bubble>): Flow<DataResource<Unit>>
    fun deleteBubbles(bubbles: List<Bubble>): Flow<DataResource<Unit>>
    fun updateBubbles(bubbles: List<Bubble>): Flow<DataResource<Unit>>
    fun getBubbleDetail(bubbleId: Int): Flow<DataResource<Bubble>>
}