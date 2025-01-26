package com.umc.edison.data.repository

import com.umc.edison.data.bound.flowDataResource
import com.umc.edison.data.datasources.BubbleLocalDataSource
import com.umc.edison.data.datasources.BubbleRemoteDataSource
import com.umc.edison.data.model.toData
import com.umc.edison.domain.DataResource
import com.umc.edison.domain.model.Bubble
import com.umc.edison.domain.repository.BubbleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BubbleRepositoryImpl @Inject constructor(
    private val bubbleLocalDataSource: BubbleLocalDataSource,
    private val bubbleRemoteDataSource: BubbleRemoteDataSource
) : BubbleRepository {

    override fun getAllBubbles(): Flow<DataResource<List<Bubble>>> = flowDataResource(
        remoteDataAction = { bubbleRemoteDataSource.getAllBubbles() },
        localDataAction = { bubbleLocalDataSource.getAllBubbles() },
        saveCacheAction = { bubbleLocalDataSource.addBubbles(it)}
    )

    override fun addBubbles(bubbles: List<Bubble>): Flow<DataResource<Unit>> = flowDataResource(
        dataAction = { bubbleLocalDataSource.addBubbles(bubbles.toData()) }
    )
}