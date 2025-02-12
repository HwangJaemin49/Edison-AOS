package com.umc.edison.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.umc.edison.local.model.BubbleLocal
import com.umc.edison.local.room.RoomConstant
import java.util.Date

@Dao
interface LinkedBubbleDao {
    @Query(
        "INSERT INTO ${RoomConstant.Table.LINKED_BUBBLE} " +
                "(curr_bubble_id, link_bubble_id, is_back, created_at, updated_at ) " +
                "VALUES (:currId, :linkedId, :isBack, :createdAt, :updatedAt)"
    )
    suspend fun insert(
        currId: Int,
        linkedId: Int,
        isBack: Boolean,
        createdAt: Date = Date(),
        updatedAt: Date = Date()
    )

    @Query(
        "SELECT * FROM ${RoomConstant.Table.BUBBLE} " +
                "WHERE id IN (SELECT link_bubble_id FROM ${RoomConstant.Table.LINKED_BUBBLE} WHERE curr_bubble_id = :currId AND is_back = 0)"
    )
    suspend fun getLinkedBubbleByBubbleId(currId: Int): BubbleLocal?

    @Query(
        "SELECT * FROM ${RoomConstant.Table.BUBBLE} " +
                "WHERE id IN (SELECT link_bubble_id FROM ${RoomConstant.Table.LINKED_BUBBLE} WHERE curr_bubble_id = :currId AND is_back = 1)"
    )
    suspend fun getBackLinksByBubbleId(currId: Int): List<BubbleLocal>

    @Query("SELECT id FROM ${RoomConstant.Table.LINKED_BUBBLE} WHERE curr_bubble_id = :currId AND link_bubble_id = :linkedId AND is_back = :isBack")
    suspend fun getLinkedBubbleId(currId: Int, linkedId: Int, isBack: Boolean): Int?

    @Query("DELETE FROM ${RoomConstant.Table.LINKED_BUBBLE} WHERE curr_bubble_id = :currId AND is_back = :isBack")
    suspend fun deleteLinkedBubble(currId: Int, isBack: Boolean)
}