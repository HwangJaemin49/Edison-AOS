package com.umc.edison.data.repository

import com.umc.edison.data.bound.flowDataResource
import com.umc.edison.data.datasources.BubbleLocalDataSource
import com.umc.edison.data.datasources.BubbleRemoteDataSource
import com.umc.edison.data.model.toData
import com.umc.edison.domain.DataResource
import com.umc.edison.domain.model.Bubble
import com.umc.edison.domain.model.ClusteredBubblePosition
import com.umc.edison.domain.repository.BubbleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BubbleRepositoryImpl @Inject constructor(
    private val bubbleLocalDataSource: BubbleLocalDataSource,
    private val bubbleRemoteDataSource: BubbleRemoteDataSource,
) : BubbleRepository {

    override fun getAllBubbles(): Flow<DataResource<List<Bubble>>> = flowDataResource(
        dataAction = { bubbleLocalDataSource.getAllBubbles() },
    )

    override fun getStorageBubbles(): Flow<DataResource<List<Bubble>>> = flowDataResource(
        dataAction = { bubbleLocalDataSource.getStorageBubbles()},
    )

    override fun addBubbles(bubbles: List<Bubble>): Flow<DataResource<Unit>> = flowDataResource(
        dataAction = { bubbleLocalDataSource.addBubbles(bubbles.toData()) }
    )

    override fun getSearchBubbles(query: String): Flow<DataResource<List<Bubble>>> = flowDataResource(
        dataAction = { bubbleLocalDataSource.getSearchBubbles(query) }
    )

    override fun softDeleteBubbles(bubbles: List<Bubble>): Flow<DataResource<Unit>> =
        flowDataResource(
            dataAction = { bubbleLocalDataSource.moveBubblesToTrash(bubbles.toData()) }
        )

    override fun updateBubble(bubble: Bubble): Flow<DataResource<Bubble>> = flowDataResource(
        dataAction = { bubbleLocalDataSource.updateBubble(bubble.toData()) }
    )

    override fun updateBubbles(bubbles: List<Bubble>): Flow<DataResource<Unit>> = flowDataResource(
        dataAction = { bubbleLocalDataSource.updateBubbles(bubbles.toData()) }
    )

    override fun getTrashedBubbles(): Flow<DataResource<List<Bubble>>> = flowDataResource(
        dataAction = { bubbleLocalDataSource.getTrashedBubbles() },
    )

    override fun getBubble(bubbleId: Int): Flow<DataResource<Bubble>> = flowDataResource(
        dataAction = { bubbleLocalDataSource.getBubble(bubbleId) }
    )

    override fun recoverBubbles(bubbles: List<Bubble>): Flow<DataResource<Unit>> = flowDataResource(
        dataAction = { bubbleLocalDataSource.recoverBubbles(bubbles.toData()) }
    )

    override fun deleteBubbles(bubbles: List<Bubble>): Flow<DataResource<Unit>> = flowDataResource(
        dataAction = { bubbleLocalDataSource.softDeleteBubbles(bubbles.toData()) }
    )

    override fun addBubble(bubble: Bubble): Flow<DataResource<Bubble>> = flowDataResource(
        dataAction = { bubbleLocalDataSource.addBubble(bubble.toData()) }
    )

    override fun getClusteredBubblesPosition(): Flow<DataResource<List<ClusteredBubblePosition>>> = flowDataResource(
        dataAction = { bubbleRemoteDataSource.getBubblePosition() }
    )
}