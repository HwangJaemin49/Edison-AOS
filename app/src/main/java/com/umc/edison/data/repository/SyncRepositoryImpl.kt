package com.umc.edison.data.repository

import android.util.Log
import com.umc.edison.data.datasources.BubbleLocalDataSource
import com.umc.edison.data.datasources.BubbleRemoteDataSource
import com.umc.edison.data.datasources.LabelLocalDataSource
import com.umc.edison.data.datasources.LabelRemoteDataSource
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
) : SyncRepository {
    override suspend fun syncLabelData() {
        withContext(Dispatchers.IO) {
            val unSyncedLocalLabels: List<LabelEntity> = labelLocalDataSource.getUnSyncedLabels()
            unSyncedLocalLabels.forEach { label ->
                try {
                    val syncedLabel = labelRemoteDataSource.syncLabel(label)

                    if (syncedLabel.same(label)) {
                        Log.d("SyncRepositoryImpl", "Label with id: ${label.id} is synced")
                        labelLocalDataSource.markAsSynced(label)

                        if (syncedLabel.isDeleted) {
                            labelLocalDataSource.deleteLabel(label)
                        }
                    } else {
                        Log.e("SyncRepositoryImpl", "Label with id: ${label.id} is not synced")
                    }
                } catch (e: Exception) {
                    Log.e("SyncRepositoryImpl", "Failed to sync label with id: ${label.id}", e)
                    throw e
                }
            }
        }
    }

    override suspend fun syncBubbleData() {
        withContext(Dispatchers.IO) {
            val unSyncedLocalBubbles: List<BubbleEntity> =
                bubbleLocalDataSource.getUnSyncedBubbles()
            unSyncedLocalBubbles.forEach { bubble ->
                try {
                    bubbleRemoteDataSource.syncBubble(bubble)
                    bubbleLocalDataSource.markAsSynced(bubble)
                } catch (e: Exception) {
                    Log.e("SyncRepositoryImpl", "Failed to sync bubble with id: ${bubble.id}", e)
                    throw e
                }
            }
        }
    }
}