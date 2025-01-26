package com.umc.edison.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.umc.edison.local.room.RoomConstant
import java.util.Date

@Dao
interface BubbleLabelDao {
    @Query(
        "INSERT INTO ${RoomConstant.Table.BUBBLE_LABEL} " +
                "(bubble_id, label_id, created_at, updated_at) " +
                "VALUES (:bubbleId, :labelId, :createdAt, :updatedAt)"
    )
    fun insert(
        bubbleId: Int,
        labelId: Int,
        createdAt: Date = Date(),
        updatedAt: Date = Date()
    )
}
