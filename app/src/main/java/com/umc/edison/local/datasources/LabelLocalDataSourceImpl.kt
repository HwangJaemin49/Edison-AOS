package com.umc.edison.local.datasources

import android.util.Log
import com.umc.edison.data.datasources.LabelLocalDataSource
import com.umc.edison.data.model.LabelEntity
import com.umc.edison.local.model.LabelLocal
import com.umc.edison.local.model.toData
import com.umc.edison.local.model.toLocal
import com.umc.edison.local.room.RoomConstant
import com.umc.edison.local.room.dao.BubbleDao
import com.umc.edison.local.room.dao.LabelDao
import javax.inject.Inject

class LabelLocalDataSourceImpl @Inject constructor(
    private val bubbleDao: BubbleDao,
    private val labelDao: LabelDao
) : LabelLocalDataSource, BaseLocalDataSourceImpl<LabelLocal>(labelDao) {

    private val tableName = RoomConstant.getTableNameByClass(LabelLocal::class.java)

    override suspend fun getAllLabels(): List<LabelEntity> {
        val labels = labelDao.getAllLabels().toData()

        labels.map { label ->
            label.bubbleCnt = bubbleDao.getBubbleCntByLabelId(label.id)
        }

        Log.d("LabelLocalDataSourceImplImpl", "getAllLabels: $labels")

        return labels
    }

    override suspend fun addLabels(labels: List<LabelEntity>) {
        labels.forEach { label ->
            addLabel(label)
        }
    }

    override suspend fun addLabel(label: LabelEntity) {
        Log.d("LocalDataSource: addLabel", label.toString())
        Log.d("label color", label.toLocal().color.toString())
        insert(label.toLocal())
    }

    override suspend fun updateLabel(label: LabelEntity) {
        Log.d("LocalDataSource: updateLabel", label.toString())
        update(label.toLocal())
    }

    override suspend fun softDeleteLabel(label: LabelEntity) {
        Log.d("LocalDataSource: softDeleteLabel", label.toString())
        softDelete(label.toLocal())
    }

    override suspend fun deleteLabel(label: LabelEntity) {
        labelDao.delete(label.toLocal())
    }

    override suspend fun getUnsyncedLabels(): List<LabelEntity> {
        return getUnsyncedDatas(tableName).toData()
    }

    override suspend fun markAsSynced(label: LabelEntity) {
        markAsSynced(tableName, label.id)
    }

}