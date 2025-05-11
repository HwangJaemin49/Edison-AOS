package com.umc.edison.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.umc.edison.local.room.RoomConstant
import java.util.Date

@Dao
interface BubbleLabelDao {
    // CREATE
    @Query(
        "INSERT INTO ${RoomConstant.Table.BUBBLE_LABEL} " +
                "(bubble_id, label_id, created_at, updated_at) " +
                "VALUES (:bubbleId, :labelId, :createdAt, :updatedAt)"
    )
    suspend fun insert(
        bubbleId: String,
        labelId: String,
        createdAt: Date = Date(),
        updatedAt: Date = Date()
    )

    // READ
    @Query("SELECT id FROM ${RoomConstant.Table.BUBBLE_LABEL} WHERE bubble_id = :bubbleId AND label_id = :labelId")
    suspend fun getBubbleLabelId(bubbleId: String, labelId: String): Int?

    // DELETE
    @Query("DELETE FROM ${RoomConstant.Table.BUBBLE_LABEL} WHERE bubble_id = :bubbleId")
    suspend fun deleteByBubbleId(bubbleId: String)

}
