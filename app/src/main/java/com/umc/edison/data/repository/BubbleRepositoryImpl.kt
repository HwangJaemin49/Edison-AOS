package com.umc.edison.data.repository

import com.umc.edison.data.bound.flowDataResource
import com.umc.edison.data.datasources.BubbleLocalDataSource
import com.umc.edison.data.datasources.BubbleRemoteDataSource
import com.umc.edison.data.datasources.UserRemoteDataSource
import com.umc.edison.data.model.toData
import com.umc.edison.domain.DataResource
import com.umc.edison.domain.model.Bubble
import com.umc.edison.domain.repository.BubbleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BubbleRepositoryImpl @Inject constructor(
    private val bubbleLocalDataSource: BubbleLocalDataSource,
    private val bubbleRemoteDataSource: BubbleRemoteDataSource,
    private val userRemoteDataSource: UserRemoteDataSource
) : BubbleRepository {

    override fun getAllBubbles(): Flow<DataResource<List<Bubble>>> = flowDataResource(
        remoteDataAction = { bubbleRemoteDataSource.getAllBubbles() },
        localDataAction = { bubbleLocalDataSource.getAllBubbles() },
        saveCacheAction = { bubbleLocalDataSource.addBubbles(it)}
    )

    override fun addBubbles(bubbles: List<Bubble>): Flow<DataResource<Unit>> = flowDataResource(
        dataAction = { bubbleLocalDataSource.addBubbles(bubbles.toData()) }
    )

    override fun softDeleteBubbles(bubbles: List<Bubble>): Flow<DataResource<Unit>> = flowDataResource(
        dataAction = { bubbleLocalDataSource.moveBubblesToTrash(bubbles.toData()) }
    )

    override fun updateBubbles(bubbles: List<Bubble>): Flow<DataResource<Unit>> = flowDataResource(
        dataAction = { bubbleLocalDataSource.updateBubbles(bubbles.toData()) }
    )

    override fun getDeletedBubbles(): Flow<DataResource<List<Bubble>>> = flowDataResource(
        remoteDataAction = { userRemoteDataSource.getDeletedBubbles() },
        localDataAction = { bubbleLocalDataSource.getDeletedBubbles() },
        saveCacheAction = { bubbleLocalDataSource.addBubbles(it) }
    )

    override fun recoverBubbles(bubbles: List<Bubble>): Flow<DataResource<Unit>> = flowDataResource(
        dataAction = { bubbleLocalDataSource.recoverBubbles(bubbles.toData()) }
    )
}