package com.umc.edison.local.datasources

import android.icu.util.Calendar
import com.umc.edison.data.datasources.BubbleLocalDataSource
import com.umc.edison.data.model.BubbleEntity
import com.umc.edison.local.model.BubbleLocal
import com.umc.edison.local.model.LabelLocal
import com.umc.edison.local.model.toLocal
import com.umc.edison.local.room.RoomConstant
import com.umc.edison.local.room.dao.BubbleDao
import com.umc.edison.local.room.dao.BubbleLabelDao
import com.umc.edison.local.room.dao.LabelDao
import com.umc.edison.local.room.dao.LinkedBubbleDao
import javax.inject.Inject

class BubbleLocalDataSourceImpl @Inject constructor(
    private val bubbleDao: BubbleDao,
    private val labelDao: LabelDao,
    private val bubbleLabelDao: BubbleLabelDao,
    private val linkedBubbleDao: LinkedBubbleDao
) : BubbleLocalDataSource, BaseLocalDataSourceImpl<BubbleLocal>(bubbleDao) {

    private val tableName = RoomConstant.getTableNameByClass(BubbleLocal::class.java)

    override suspend fun getAllBubbles(): List<BubbleEntity> {
        val localBubbles: List<BubbleLocal> = bubbleDao.getAllBubbles()
        return convertLocalBubblesToBubbles(localBubbles)
    }

    override suspend fun getStorageBubbles(): List<BubbleEntity> {
        val sevenDaysAgo = Calendar.getInstance().apply {
            add(Calendar.DAY_OF_YEAR, -7)
        }.time.time
        val localBubbles: List<BubbleLocal> = bubbleDao.getStorageBubbles(sevenDaysAgo)

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

    override suspend fun getSearchBubbles(query: String): List<BubbleEntity> {
        val localBubbles: List<BubbleLocal> = bubbleDao.getSearchBubbles(query)
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
        val bubble = bubbleDao.getBubbleById(bubbleId)?.toData() ?: return BubbleEntity(0)

        bubble.labels = labelDao.getAllLabelsByBubbleId(bubbleId).map { it.toData() }
        bubble.linkedBubble = linkedBubbleDao.getLinkedBubbleByBubbleId(bubbleId)?.toData()
        bubble.backLinks = linkedBubbleDao.getBackLinksByBubbleId(bubbleId).map { it.toData() }

        return bubble
    }

    override suspend fun moveBubblesToTrash(bubbles: List<BubbleEntity>) {
        bubbles.map { bubble ->
            moveBubbleToTrash(bubble)
        }
    }

    override suspend fun moveBubbleToTrash(bubble: BubbleEntity) {
        val localBubble = bubble.toLocal()
        localBubble.isTrashed = true

        update(localBubble, tableName)
    }

    override suspend fun updateBubbles(bubbles: List<BubbleEntity>) {
        bubbles.map { bubble ->
            updateBubble(bubble)
        }
    }

    override suspend fun updateBubble(bubble: BubbleEntity): BubbleEntity {
        update(bubble.toLocal(), tableName)

        bubbleLabelDao.deleteByBubbleId(bubble.id)
        linkedBubbleDao.deleteLinkedBubble(bubble.id, false)

        addBubbleLabel(bubble)
        addLinkedBubble(bubble)

        return getBubble(bubble.id)
    }

    override suspend fun syncBubbles(bubbles: List<BubbleEntity>) {
        bubbles.map { bubble ->
            bubbleDao.sync(bubble.toLocal())

            addBubbleLabel(bubble)
        }

        bubbles.map { bubble ->
            addLinkedBubble(bubble)
        }
    }

    override suspend fun addBubbles(bubbles: List<BubbleEntity>) {
        bubbles.map { bubble ->
            addBubble(bubble)
        }
    }

    override suspend fun addBubble(bubble: BubbleEntity): BubbleEntity {
        val id = insert(bubble.toLocal())
        bubble.id = id.toInt()

        addBubbleLabel(bubble)
        addLinkedBubble(bubble)

        return getBubble(bubble.id)
    }

    override suspend fun getUnSyncedBubbles(): List<BubbleEntity> {
        val localBubbles = getUnSyncedDatas(tableName)

        return convertLocalBubblesToBubbles(localBubbles)
    }

    override suspend fun markAsSynced(bubble: BubbleEntity) {
        markAsSynced(tableName, bubble.id)
    }

    override suspend fun getTrashedBubbles(): List<BubbleEntity> {
        val deletedBubbles = bubbleDao.getTrashedBubbles()

        return convertLocalBubblesToBubbles(deletedBubbles)
    }

    override suspend fun recoverBubbles(bubbles: List<BubbleEntity>) {
        bubbles.map { bubble ->
            recoverBubble(bubble)
        }
    }

    override suspend fun recoverBubble(bubble: BubbleEntity) {
        val localBubble = bubble.toLocal()
        localBubble.isTrashed = false

        update(localBubble, tableName)
    }

    override suspend fun softDeleteBubbles(bubbles: List<BubbleEntity>) {
        bubbles.map { bubble ->
            softDeleteBubble(bubble)
        }
    }

    override suspend fun softDeleteBubble(bubble: BubbleEntity) {
        softDelete(bubble.toLocal(), tableName)
    }

    override suspend fun deleteBubble(bubble: BubbleEntity) {
        bubbleDao.delete(bubble.toLocal())
    }

    private suspend fun addBubbleLabel(bubble: BubbleEntity) {
        bubble.labels.map { label ->
            val localLabel: LabelLocal? = labelDao.getLabelById(label.id)
            if (localLabel == null) {
                val labelId: Long = labelDao.insert(label.toLocal())
                bubbleLabelDao.insert(bubble.id, labelId.toInt())
            } else {
                val id = bubbleLabelDao.getBubbleLabelId(bubble.id, localLabel.id)
                if (id == null) bubbleLabelDao.insert(bubble.id, localLabel.id)
            }
        }
    }

    override suspend fun addLinkedBubble(bubble: BubbleEntity) {
        bubble.linkedBubble?.let { linkedBubble ->
            val id = linkedBubbleDao.getLinkedBubbleId(bubble.id, linkedBubble.id, false)
            if (id == null) linkedBubbleDao.insert(bubble.id, linkedBubble.id, false)
        }

        bubble.backLinks.map { backLink ->
            bubbleDao.getBubbleById(backLink.id) ?: return@map
            val id = linkedBubbleDao.getLinkedBubbleId(bubble.id, backLink.id, true)
            if (id == null) linkedBubbleDao.insert(bubble.id, backLink.id, true)
        }
    }

    override suspend fun deleteAllBubbles() {
        bubbleDao.deleteAllBubbles()
    }
}