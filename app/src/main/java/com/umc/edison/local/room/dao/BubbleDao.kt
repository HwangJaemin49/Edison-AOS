package com.umc.edison.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.umc.edison.local.model.BubbleLocal
import com.umc.edison.local.room.RoomConstant

@Dao
interface BubbleDao : BaseDao<BubbleLocal> {
    @Query("SELECT * FROM ${RoomConstant.Table.BUBBLE}")
    fun getAllBubbles(): List<BubbleLocal>

    @Query("SELECT COUNT(*) FROM ${RoomConstant.Table.BUBBLE_LABEL} WHERE label_id = :labelId")
    fun getBubbleCntByLabelId(labelId: Int): Int

    @Query("SELECT * FROM ${RoomConstant.Table.BUBBLE} WHERE is_synced = 0")
    fun getUnsyncedBubbles(): List<BubbleLocal>

    @Query("UPDATE ${RoomConstant.Table.BUBBLE} SET is_synced = 1 WHERE id = :bubbleId")
    fun markAsSynced(bubbleId: Int)
}