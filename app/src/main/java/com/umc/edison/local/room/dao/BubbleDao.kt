package com.umc.edison.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.umc.edison.local.model.BubbleLocal
import com.umc.edison.local.room.RoomConstant

@Dao
interface BubbleDao : BaseDao<BubbleLocal> {
    @Query("SELECT * FROM ${RoomConstant.Table.BUBBLE}")
    fun getAllBubbles(): List<BubbleLocal>

    @Query("SELECT * FROM ${RoomConstant.Table.BUBBLE} WHERE isSynced = 0")
    fun getNotSyncedBubbles(): List<BubbleLocal>

    @Query("UPDATE ${RoomConstant.Table.BUBBLE} SET isSynced = 1 WHERE id IN (:ids)")
    fun updateSyncedBubbles(ids: List<Int>)

    @Query("SELECT COUNT(*) FROM ${RoomConstant.Table.BUBBLE_LABEL} WHERE labelId = :labelId")
    fun getBubbleCntByLabelId(labelId: Int): Int
}