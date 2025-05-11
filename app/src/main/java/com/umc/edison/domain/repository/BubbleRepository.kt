package com.umc.edison.domain.repository

import com.umc.edison.domain.DataResource
import com.umc.edison.domain.model.bubble.Bubble
import com.umc.edison.domain.model.bubble.ClusteredBubble
import kotlinx.coroutines.flow.Flow

interface BubbleRepository {
    // CREATE
    fun addBubbles(bubbles: List<Bubble>): Flow<DataResource<Unit>>
    fun addBubble(bubble: Bubble): Flow<DataResource<Bubble>>

    // READ
    fun getAllBubbles(): Flow<DataResource<List<Bubble>>>
    fun getAllClusteredBubbles(): Flow<DataResource<List<ClusteredBubble>>>
    fun getAllRecentBubbles(dayBefore: Int): Flow<DataResource<List<Bubble>>>
    fun getAllTrashedBubbles(): Flow<DataResource<List<Bubble>>>
    fun getBubble(id: String): Flow<DataResource<Bubble>>
    fun searchBubbles(query: String): Flow<DataResource<List<Bubble>>>

    // UPDATE
    fun recoverBubbles(bubbles: List<Bubble>): Flow<DataResource<Unit>>
    fun updateBubbles(bubbles: List<Bubble>): Flow<DataResource<Unit>>
    fun updateBubble(bubble: Bubble): Flow<DataResource<Bubble>>

    // DELETE
    fun deleteBubbles(bubbles: List<Bubble>): Flow<DataResource<Unit>>
    fun trashBubbles(bubbles: List<Bubble>): Flow<DataResource<Unit>>
}