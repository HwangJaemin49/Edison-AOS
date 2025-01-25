package com.umc.edison.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    indices = [Index(value = ["bubble_id"]), Index(value = ["label_id"])],
    foreignKeys = [
        ForeignKey(
            entity = BubbleLocal::class,
            parentColumns = ["id"],
            childColumns = ["bubble_id"],
            onDelete = ForeignKey.CASCADE // Bubble 삭제 시 관련 BubbleLabel도 삭제
        ),
        ForeignKey(
            entity = LabelLocal::class,
            parentColumns = ["id"],
            childColumns = ["label_id"],
            onDelete = ForeignKey.CASCADE // Label 삭제 시 관련 BubbleLabel도 삭제
        )
    ]
)
data class BubbleLabelLocal(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "bubble_id") val bubbleId: Int,
    @ColumnInfo(name = "label_id") val labelId: Int,
    @ColumnInfo(name = "created_at") override var createdAt: Long? = null,
    @ColumnInfo(name = "updated_at") override var updatedAt: Long? = null,
) : BaseLocal
