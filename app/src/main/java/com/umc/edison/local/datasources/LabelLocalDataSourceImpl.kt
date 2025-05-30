package com.umc.edison.local.datasources

import android.util.Log
import com.umc.edison.data.datasources.LabelLocalDataSource
import com.umc.edison.data.model.label.LabelEntity
import com.umc.edison.local.model.LabelLocal
import com.umc.edison.local.model.toLocal
import com.umc.edison.local.room.RoomConstant
import com.umc.edison.local.room.dao.LabelDao
import javax.inject.Inject

class LabelLocalDataSourceImpl @Inject constructor(
    private val labelDao: LabelDao,
) : LabelLocalDataSource, BaseLocalDataSourceImpl<LabelLocal>(labelDao) {

    private val tableName = RoomConstant.getTableNameByClass(LabelLocal::class.java)

    // CREATE
    override suspend fun addLabel(label: LabelEntity) {
        insert(label.toLocal())
    }

    // READ
    override suspend fun getAllLabels(): List<LabelEntity> {
        labelDao.getAllLabels().forEach {
            Log.i("LabelLocalDataSourceImpl", "Label: ${it.uuid}, ${it.name}, ${it.color}")
        }
        return labelDao.getAllLabels().map { it.toData() }
    }

    override suspend fun getLabel(labelId: String): LabelEntity {
        val localLabel = labelDao.getLabelById(labelId) ?: throw Exception("Label not found")

        return localLabel.toData()
    }

    override suspend fun getUnSyncedLabels(): List<LabelEntity> {
        return getAllUnSyncedRows(tableName).map { it.toData() }
    }

    // UPDATE
    override suspend fun markAsSynced(label: LabelEntity) {
        markAsSynced(tableName, label.id)
    }

    override suspend fun updateLabel(label: LabelEntity) {
        update(label.toLocal(), tableName)
    }

    // DELETE
    override suspend fun deleteLabel(label: LabelEntity) {
        labelDao.delete(label.toLocal())
    }
}