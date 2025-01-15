package com.umc.edison.local.datasources

import android.util.Log
import com.umc.edison.data.datasources.LabelLocalDataSource
import com.umc.edison.data.model.LabelEntity
import com.umc.edison.local.model.toData
import com.umc.edison.local.model.toLocal
import com.umc.edison.local.room.dao.BubbleDao
import com.umc.edison.local.room.dao.LabelDao
import javax.inject.Inject

class LabelLocalDataSourceImpl @Inject constructor(
    private val bubbleDao: BubbleDao,
    private val labelDao: LabelDao
) : LabelLocalDataSource {
    override suspend fun getAllLabels(): List<LabelEntity> {
        val labels = labelDao.getAllLabels().toData()

        labels.map { label ->
            label.bubbleCnt = bubbleDao.getBubbleCntByLabelId(label.id)
        }

        Log.d("LabelLocalDataSourceImpl", "getAllLabels: $labels")

        return labels
    }

    override suspend fun getNotSyncedLabels(): List<LabelEntity> {
        val labels = labelDao.getNotSyncedLabels().toData()

        labels.map { label ->
            label.bubbleCnt = bubbleDao.getBubbleCntByLabelId(label.id)
        }

        return labels
    }

    override suspend fun addLabel(label: LabelEntity) {
        labelDao.insert(label.toLocal())
    }

    override suspend fun updateSyncedLabels(labels: List<LabelEntity>) {
        labelDao.updateSyncedLabels(labels.map { it.id })
    }

}