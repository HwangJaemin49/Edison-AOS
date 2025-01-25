package com.umc.edison.local.datasources

import com.umc.edison.data.datasources.BubbleLocalDataSource
import com.umc.edison.data.model.BubbleEntity
import com.umc.edison.local.model.BubbleLocal
import com.umc.edison.local.model.toData
import com.umc.edison.local.room.RoomConstant
import com.umc.edison.local.room.dao.BubbleDao
import com.umc.edison.local.room.dao.LabelDao
import javax.inject.Inject

class BubbleLocalDataSourceImpl @Inject constructor(
    private val bubbleDao: BubbleDao,
    private val labelDao: LabelDao
) : BubbleLocalDataSource, BaseLocalDataSourceImpl<BubbleLocal>(bubbleDao) {

    private val tableName = RoomConstant.getTableNameByClass(BubbleLocal::class.java)

    override suspend fun getAllBubbles(): List<BubbleEntity> {
        val bubbles = bubbleDao.getAllBubbles().toData()

        bubbles.map { bubble ->
            bubble.labels = labelDao.getAllLabelsByBubbleId(bubble.id).toData()
        }

        return bubbles
    }

    override suspend fun addBubbles(bubbles: List<BubbleEntity>) {
        TODO("Not yet implemented")
    }

    override suspend fun addBubble(bubble: BubbleEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun getUnSyncedBubbles(): List<BubbleEntity> {
        return getUnsyncedDatas(tableName).toData()
    }

    override suspend fun markAsSynced(bubble: BubbleEntity) {
        markAsSynced(tableName, bubble.id)
    }
}