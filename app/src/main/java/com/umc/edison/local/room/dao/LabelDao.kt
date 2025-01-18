package com.umc.edison.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.umc.edison.local.model.LabelLocal
import com.umc.edison.local.room.RoomConstant

@Dao
interface LabelDao : BaseDao<LabelLocal> {

    @Query("SELECT * FROM ${RoomConstant.Table.LABEL}")
    fun getAllLabels(): List<LabelLocal>

    @Query(
        "SELECT * FROM ${RoomConstant.Table.LABEL} " +
                "Where id IN (" +
                "SELECT labelId FROM ${RoomConstant.Table.BUBBLE_LABEL} WHERE bubbleId = :bubbleId" +
                ")"
    )
    fun getAllLabelsByBubbleId(bubbleId: Int): List<LabelLocal>

    @Query("UPDATE ${RoomConstant.Table.LABEL} SET isSynced = 1 WHERE id IN (:ids)")
    fun updateSyncedLabels(ids: List<Int>)

    @Query("SELECT * FROM ${RoomConstant.Table.LABEL} WHERE isSynced = 0")
    fun getNotSyncedLabels(): List<LabelLocal>

    @Query("UPDATE ${RoomConstant.Table.LABEL} SET name = :name, color = :color WHERE id = :labelId")
    fun updateLabel(labelId: Int, name: String, color: Int)
}