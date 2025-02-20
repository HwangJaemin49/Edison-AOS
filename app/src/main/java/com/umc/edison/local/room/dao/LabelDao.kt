package com.umc.edison.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.umc.edison.local.model.LabelLocal
import com.umc.edison.local.room.RoomConstant

@Dao
interface LabelDao : BaseDao<LabelLocal> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun sync(labelLocal: LabelLocal)

    @Query("SELECT * FROM ${RoomConstant.Table.LABEL} WHERE is_deleted = 0")
    suspend fun getAllLabels(): List<LabelLocal>

    @Query(
        "SELECT * FROM ${RoomConstant.Table.LABEL} " +
                "Where id IN (" +
                    "SELECT label_id FROM ${RoomConstant.Table.BUBBLE_LABEL} WHERE bubble_id = :bubbleId" +
                ") AND is_deleted = 0"
    )
    suspend fun getAllLabelsByBubbleId(bubbleId: Int): List<LabelLocal>

    @Query("SELECT * FROM ${RoomConstant.Table.LABEL} WHERE id = :labelId")
    suspend fun getLabelById(labelId: Int): LabelLocal?

    @Query("DELETE FROM ${RoomConstant.Table.LABEL}")
    suspend fun deleteAllLabels()
}