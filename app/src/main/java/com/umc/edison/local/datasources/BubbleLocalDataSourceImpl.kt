package com.umc.edison.local.datasources

import android.util.Log
import com.umc.edison.data.datasources.BubbleLocalDataSource
import com.umc.edison.data.model.BubbleEntity
import com.umc.edison.local.model.BubbleLocal
import com.umc.edison.local.model.LabelLocal
import com.umc.edison.local.model.toLocal
import com.umc.edison.local.room.RoomConstant
import com.umc.edison.local.room.dao.BubbleDao
import com.umc.edison.local.room.dao.BubbleLabelDao
import com.umc.edison.local.room.dao.LabelDao
import javax.inject.Inject

class BubbleLocalDataSourceImpl @Inject constructor(
    private val bubbleDao: BubbleDao,
    private val labelDao: LabelDao,
    private val bubbleLabelDao: BubbleLabelDao,
) : BubbleLocalDataSource, BaseLocalDataSourceImpl<BubbleLocal>(bubbleDao) {

    private val tableName = RoomConstant.getTableNameByClass(BubbleLocal::class.java)

    override suspend fun getAllBubbles(): List<BubbleEntity> {
        val localBubbles: List<BubbleLocal> = bubbleDao.getAllBubbles()
        return convertLocalBubblesToBubbles(localBubbles)
    }

    override suspend fun getBubblesByLabel(labelId: Int): List<BubbleEntity> {
        val localBubbles: List<BubbleLocal> = if (labelId == 0) {
            bubbleDao.getBubblesWithoutLabel()
        } else {
            bubbleDao.getBubblesByLabel(labelId)
        }
        return convertLocalBubblesToBubbles(localBubbles)
    }

    private suspend fun convertLocalBubblesToBubbles(localBubbles: List<BubbleLocal>): List<BubbleEntity> {
        val bubbles: MutableList<BubbleEntity> = mutableListOf()

        localBubbles.map {
            bubbles += getBubble(it.id)
        }

        return bubbles
    }

    override suspend fun getBubble(bubbleId: Int): BubbleEntity {
        val bubble = bubbleDao.getBubbleById(bubbleId).toData()
        bubble.labels = labelDao.getAllLabelsByBubbleId(bubbleId).map { it.toData() }
        return bubble
    }

    override suspend fun deleteBubbles(bubbles: List<BubbleEntity>) {
        bubbles.map { bubble ->
            deleteBubble(bubble)
        }
    }

    override suspend fun deleteBubble(bubble: BubbleEntity) {
        softDelete(bubble.toLocal(), tableName)
    }

    override suspend fun updateBubbles(bubbles: List<BubbleEntity>) {
        bubbles.map { bubble ->
            updateBubble(bubble)
        }
    }

    override suspend fun updateBubble(bubble: BubbleEntity) {
        update(bubble.toLocal(), tableName)

        bubbleLabelDao.deleteByBubbleId(bubble.id)
        addBubbleLabel(bubble)
    }


    override suspend fun addBubbles(bubbles: List<BubbleEntity>) {
        bubbles.map { bubble ->
            addBubble(bubble)
        }
    }

    override suspend fun addBubble(bubble: BubbleEntity) {
        insert(bubble.toLocal())

        addBubbleLabel(bubble)
    }

    override suspend fun getUnSyncedBubbles(): List<BubbleEntity> {
        return getUnSyncedDatas(tableName).map { it.toData() }
    }

    override suspend fun markAsSynced(bubble: BubbleEntity) {
        markAsSynced(tableName, bubble.id)
    }

    override suspend fun getDeletedBubbles(): List<BubbleEntity> {
        val deletedBubbles = bubbleDao.getDeletedBubbles().map { it.toData() }

        deletedBubbles.map { bubble ->
            bubble.labels = labelDao.getAllLabelsByBubbleId(bubble.id).map { it.toData() }
        }

        return deletedBubbles
    }

    override suspend fun recoverBubbles(bubbles: List<BubbleEntity>) {
        bubbles.map { bubble ->
            recoverBubble(bubble)
        }
    }

    override suspend fun recoverBubble(bubble: BubbleEntity) {
        val id = recover(bubble.toLocal(), tableName)

        Log.i("BubbleLocalDataSourceImpl", "recoverBubble: $id, bubble: $bubble")

        addBubbleLabel(bubble)
    }

    private suspend fun addBubbleLabel(bubble: BubbleEntity) {
        bubble.labels.map { label ->
            val localLabel: LabelLocal? = labelDao.getLabelById(label.id)
            if (localLabel == null) {
                val labelId: Long = labelDao.insert(label.toLocal())
                bubbleLabelDao.insert(bubble.id, labelId.toInt())
            } else {
                bubbleLabelDao.insert(bubble.id, localLabel.id)
            }
        }
    }

    override suspend fun getBubbleDetail(bubbleId: Int): BubbleEntity {
        // bubbleId로 Bubble 데이터를 가져옴
        val localBubble = bubbleDao.getBubbleById(bubbleId) ?: throw Exception("Bubble not found")

        // Bubble 데이터를 도메인 계층 데이터로 변환
        val bubble = localBubble.toData()

        // Bubble에 연결된 Label 데이터를 추가
        bubble.labels = labelDao.getAllLabelsByBubbleId(bubbleId).map { it.toData() }

        return bubble
    }


}