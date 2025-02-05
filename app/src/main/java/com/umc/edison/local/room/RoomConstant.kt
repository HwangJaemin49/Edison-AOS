package com.umc.edison.local.room

import com.umc.edison.local.model.BubbleLabelLocal
import com.umc.edison.local.model.BubbleLocal
import com.umc.edison.local.model.LabelLocal

object RoomConstant {
    const val ROOM_DB_NAME = "edison_database"
    const val ROOM_VERSION = 1

    object Table {
        const val BUBBLE = "BubbleLocal"
        const val LABEL = "LabelLocal"
        const val BUBBLE_LABEL = "BubbleLabelLocal"
        const val LINKED_BUBBLE = "LinkedBubbleLocal"
    }

    fun getTableNameByClass(clazz: Class<*>): String {
        return when (clazz) {
            BubbleLocal::class.java -> Table.BUBBLE
            LabelLocal::class.java -> Table.LABEL
            BubbleLabelLocal::class.java -> Table.BUBBLE_LABEL
            else -> throw IllegalArgumentException("Unknown type: $clazz")
        }
    }
}