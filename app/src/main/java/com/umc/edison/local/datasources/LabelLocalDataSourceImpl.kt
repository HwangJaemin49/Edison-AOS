package com.umc.edison.local.datasources

import com.umc.edison.data.datasources.BubbleLocalDataSource
import com.umc.edison.data.datasources.LabelLocalDataSource
import com.umc.edison.data.model.LabelEntity
import com.umc.edison.local.model.LabelLocal
import com.umc.edison.local.model.toLocal
import com.umc.edison.local.room.RoomConstant
import com.umc.edison.local.room.dao.LabelDao
import com.umc.edison.ui.theme.White000
import javax.inject.Inject

class LabelLocalDataSourceImpl @Inject constructor(
    private val labelDao: LabelDao,
    private val bubbleLocalDataSource: BubbleLocalDataSource,
) : LabelLocalDataSource, BaseLocalDataSourceImpl<LabelLocal>(labelDao) {

    private val tableName = RoomConstant.getTableNameByClass(LabelLocal::class.java)

    override suspend fun getAllLabels(): List<LabelEntity> {
        val labels = labelDao.getAllLabels().map { it.toData() }.toMutableList()

        labels.map { label ->
            label.bubbles = bubbleLocalDataSource.getBubblesByLabel(label.id)
        }

        val defaultLabel = LabelEntity(
            id = 0,
            name = "-",
            color = White000,
            bubbles = listOf()
        )

        defaultLabel.bubbles = bubbleLocalDataSource.getBubblesByLabel(0)

        if (defaultLabel.bubbles.isNotEmpty()) {
            labels += defaultLabel
        }

        return labels
    }

    override suspend fun addLabels(labels: List<LabelEntity>) {
        labels.forEach { label ->
            addLabel(label)
        }
    }

    override suspend fun addLabel(label: LabelEntity) {
        insert(label.toLocal())
    }

    override suspend fun updateLabel(label: LabelEntity) {
        update(label.toLocal(),tableName)
    }

    override suspend fun softDeleteLabel(label: LabelEntity) {
        softDelete(label.toLocal(), tableName)
        bubbleLocalDataSource.updateBubbles(label.bubbles)
    }

    override suspend fun deleteLabel(label: LabelEntity) {
        labelDao.delete(label.toLocal())
    }

    override suspend fun getLabelDetail(labelId: Int): LabelEntity {
        if (labelId == 0) {
            return LabelEntity(
                id = 0,
                name = "-",
                color = White000,
                bubbles = bubbleLocalDataSource.getBubblesByLabel(0)
            )
        }

        val localLabel = labelDao.getLabelById(labelId) ?: throw Exception("Label not found")

        val label = localLabel.toData()
        label.bubbles = bubbleLocalDataSource.getBubblesByLabel(labelId)

        return label
    }

    override suspend fun getUnSyncedLabels(): List<LabelEntity> {
        return getUnSyncedDatas(tableName).map { it.toData() }
    }

    override suspend fun markAsSynced(label: LabelEntity) {
        markAsSynced(tableName, label.id)
    }

}