package com.umc.edison.data.repository

import com.umc.edison.data.bound.flowDataResource
import com.umc.edison.data.datasources.BubbleLocalDataSource
import com.umc.edison.data.datasources.BubbleRemoteDataSource
import com.umc.edison.data.model.BubbleEntity
import com.umc.edison.domain.DataResource
import com.umc.edison.domain.model.Bubble
import com.umc.edison.domain.repository.BubbleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BubbleRepositoryImpl @Inject constructor(
    private val bubbleRemoteDataSource: BubbleRemoteDataSource,
    private val bubbleLocalDataSource: BubbleLocalDataSource
) : BubbleRepository {

    override fun getAllBubbles(): Flow<DataResource<List<Bubble>>> = flowDataResource(
        localDataAction = { bubbleLocalDataSource.getAllBubbles() },
        getNotSyncedAction = { bubbleLocalDataSource.getNotSyncedBubbles() },
        syncRemoteAction = { bubbles -> bubbleRemoteDataSource.syncBubbles(bubbles as List<BubbleEntity>) },
        updateSyncedAction = { bubbles -> bubbleLocalDataSource.updateSyncedBubbles(bubbles as List<BubbleEntity>) }
    )
}