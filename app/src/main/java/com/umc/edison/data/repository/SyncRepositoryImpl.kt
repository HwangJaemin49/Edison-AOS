package com.umc.edison.data.repository

import android.util.Log
import com.umc.edison.data.datasources.BubbleLocalDataSource
import com.umc.edison.data.datasources.BubbleRemoteDataSource
import com.umc.edison.data.datasources.LabelLocalDataSource
import com.umc.edison.data.datasources.LabelRemoteDataSource
import com.umc.edison.data.datasources.UserRemoteDataSource
import com.umc.edison.data.model.BubbleEntity
import com.umc.edison.data.model.LabelEntity
import com.umc.edison.data.model.same
import com.umc.edison.domain.repository.SyncRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SyncRepositoryImpl @Inject constructor(
    private val bubbleLocalDataSource: BubbleLocalDataSource,
    private val labelLocalDataSource: LabelLocalDataSource,
    private val bubbleRemoteDataSource: BubbleRemoteDataSource,
    private val labelRemoteDataSource: LabelRemoteDataSource,
    private val userRemoteDataSource: UserRemoteDataSource,
) : SyncRepository {
    override suspend fun syncLabelData() {
        withContext(Dispatchers.IO) {
            val loginState = userRemoteDataSource.getLogInState()
            if (!loginState) {
                return@withContext
            }

            val unSyncedLocalLabels: List<LabelEntity> = labelLocalDataSource.getUnSyncedLabels()
            unSyncedLocalLabels.forEach { label ->
                try {
                    val syncedLabel = labelRemoteDataSource.syncLabel(label)

                    if (syncedLabel.isDeleted) {
                        labelLocalDataSource.deleteLabel(label)
                    } else if (syncedLabel.same(label)) {
                        Log.d("SyncRepositoryImpl", "Label with id: ${label.id} is synced")
                        labelLocalDataSource.markAsSynced(label)
                    } else {
                        throw Exception("Label with id: ${label.id} is not synced")
                    }
                } catch (e: Throwable) {
                    Log.e("SyncRepositoryImpl", "Failed to sync label with id: ${label.id}", e)
                    throw e
                }
            }
        }
    }

    override suspend fun syncBubbleData() {
        withContext(Dispatchers.IO) {
            val loginState = userRemoteDataSource.getLogInState()
            if (!loginState) {
                return@withContext
            }

            val unSyncedLocalBubbles: List<BubbleEntity> =
                bubbleLocalDataSource.getUnSyncedBubbles()
            unSyncedLocalBubbles.forEach { bubble ->
                try {
                    val syncedBubble = bubbleRemoteDataSource.syncBubble(bubble)

                    if (syncedBubble.isDeleted) {
                        Log.d("SyncRepositoryImpl", "Bubble with id: ${bubble.id} is deleted")
                        bubbleLocalDataSource.deleteBubble(bubble)
                    } else if (syncedBubble.same(bubble)) {
                        Log.d("SyncRepositoryImpl", "Bubble with id: ${bubble.id} is synced")
                        bubbleLocalDataSource.markAsSynced(bubble)
                    } else {
                        throw Exception("Bubble with id: ${bubble.id} is not synced")
                    }

                } catch (e: Throwable) {
                    Log.e("SyncRepositoryImpl", "Failed to sync bubble with id: ${bubble.id}", e)
                    throw e
                }
            }
        }
    }

    override suspend fun syncServerDataToLocal() {
        withContext(Dispatchers.IO) {
            val loginState = userRemoteDataSource.getLogInState()
            if (!loginState) {
                return@withContext
            }

            try {
                labelRemoteDataSource.syncLabelToLocal()
            } catch (e: Throwable) {
                Log.e("SyncRepositoryImpl", "Failed to sync server's label data", e)
            }

            try {
                bubbleRemoteDataSource.syncBubbleToLocal()
            } catch (e: Throwable) {
                Log.e("SyncRepositoryImpl", "Failed to sync server's bubble data", e)
            }
        }
    }
}