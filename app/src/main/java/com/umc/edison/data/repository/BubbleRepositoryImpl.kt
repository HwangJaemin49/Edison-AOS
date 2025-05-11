package com.umc.edison.data.repository

import com.umc.edison.data.bound.flowDataResource
import com.umc.edison.data.bound.flowSyncDataResource
import com.umc.edison.data.datasources.BubbleLocalDataSource
import com.umc.edison.data.datasources.BubbleRemoteDataSource
import com.umc.edison.data.model.bubble.toData
import com.umc.edison.domain.DataResource
import com.umc.edison.domain.model.bubble.Bubble
import com.umc.edison.domain.model.bubble.ClusteredBubble
import com.umc.edison.domain.repository.BubbleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BubbleRepositoryImpl @Inject constructor(
    private val bubbleLocalDataSource: BubbleLocalDataSource,
    private val bubbleRemoteDataSource: BubbleRemoteDataSource,
) : BubbleRepository {
    // CREATE
    override fun addBubbles(bubbles: List<Bubble>): Flow<DataResource<Unit>> =
        flowSyncDataResource(
            localAction = { bubbleLocalDataSource.addBubbles(bubbles.toData()) },
            remoteSync = { bubbleRemoteDataSource.addBubbles(bubbles.toData()) },
            onRemoteSuccess = { remoteData ->
                for (bubble in remoteData) {
                    bubbleLocalDataSource.markAsSynced(bubble)
                }
            }
        )

    override fun addBubble(bubble: Bubble): Flow<DataResource<Bubble>> =
        flowSyncDataResource(
            localAction = { bubbleLocalDataSource.addBubble(bubble.toData()) },
            remoteSync = { bubbleRemoteDataSource.addBubble(bubble.toData()) },
            onRemoteSuccess = { remoteData -> bubbleLocalDataSource.markAsSynced(remoteData) }
        )

    // READ
    override fun getAllBubbles(): Flow<DataResource<List<Bubble>>> =
        flowDataResource(
            dataAction = { bubbleLocalDataSource.getAllBubbles() }
        )

    override fun getAllClusteredBubbles(): Flow<DataResource<List<ClusteredBubble>>> =
        flowDataResource(
            dataAction = { bubbleRemoteDataSource.getAllClusteredBubbles() }
        )

    override fun getAllRecentBubbles(dayBefore: Int): Flow<DataResource<List<Bubble>>> =
        flowDataResource(
            dataAction = { bubbleLocalDataSource.getAllRecentBubbles(dayBefore) }
        )

    override fun getAllTrashedBubbles(): Flow<DataResource<List<Bubble>>> =
        flowDataResource(
            dataAction = { bubbleLocalDataSource.getAllTrashedBubbles() }
        )

    override fun getBubble(id: String): Flow<DataResource<Bubble>> =
        flowDataResource(
            dataAction = { bubbleLocalDataSource.getBubble(id) }
        )

    override fun searchBubbles(query: String): Flow<DataResource<List<Bubble>>> =
        flowDataResource(
            dataAction = { bubbleLocalDataSource.getSearchBubbleResults(query) }
        )

    // UPDATE
    override fun recoverBubbles(bubbles: List<Bubble>): Flow<DataResource<Unit>> =
        flowSyncDataResource(
            localAction = { bubbleLocalDataSource.recoverBubbles(bubbles.toData()) },
            remoteSync = { bubbleRemoteDataSource.recoverBubbles(bubbles.toData()) },
            onRemoteSuccess = { remoteData ->
                for (bubble in remoteData) {
                    bubbleLocalDataSource.markAsSynced(bubble)
                }
            }
        )

    override fun updateBubbles(bubbles: List<Bubble>): Flow<DataResource<Unit>> =
        flowSyncDataResource(
            localAction = { bubbleLocalDataSource.updateBubbles(bubbles.toData()) },
            remoteSync = { bubbleRemoteDataSource.updateBubbles(bubbles.toData()) },
            onRemoteSuccess = { remoteData ->
                for (bubble in remoteData) {
                    bubbleLocalDataSource.markAsSynced(bubble)
                }
            }
        )

    override fun updateBubble(bubble: Bubble): Flow<DataResource<Bubble>> =
        flowSyncDataResource(
            localAction = { bubbleLocalDataSource.updateBubble(bubble.toData()) },
            remoteSync = { bubbleRemoteDataSource.updateBubble(bubble.toData()) },
            onRemoteSuccess = { remoteData -> bubbleLocalDataSource.markAsSynced(remoteData) }
        )

    // DELETE
    override fun deleteBubbles(bubbles: List<Bubble>): Flow<DataResource<Unit>> =
        flowSyncDataResource(
            localAction = { bubbleLocalDataSource.softDeleteBubbles(bubbles.toData()) },
            remoteSync = { bubbleRemoteDataSource.deleteBubbles(bubbles.toData()) },
            onRemoteSuccess = { remoteData -> bubbleLocalDataSource.deleteBubbles(remoteData)}
        )

    override fun trashBubbles(bubbles: List<Bubble>): Flow<DataResource<Unit>> =
        flowSyncDataResource(
            localAction = { bubbleLocalDataSource.trashBubbles(bubbles.toData()) },
            remoteSync = { bubbleRemoteDataSource.trashBubbles(bubbles.toData()) },
            onRemoteSuccess = { remoteData ->
                for (bubble in remoteData) {
                    bubbleLocalDataSource.markAsSynced(bubble)
                }
            }
        )
}