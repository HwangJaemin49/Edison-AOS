package com.umc.edison.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

/**
 * Bubble과 Bubble을 연결하는 링크 정보를 담는 데이터 클래스
 *
 * @property id 링크 정보의 ID
 * @property currBubbleId 현재 Bubble의 ID
 * @property linkBubbleId 연결된 Bubble의 ID
 * @property isBack 현재 Bubble에서 연결된 Bubble로의 방향 여부 (true: 백링크로 저장, false: 링크 버블로 저장)
 * @property createdAt 생성일
 * @property updatedAt 수정일
 */
@Entity(
    indices = [Index(value = ["curr_bubble_id", "link_bubble_id", "is_back"], unique = true), Index(value = ["curr_bubble_id"]), Index(value = ["link_bubble_id"])],
    foreignKeys = [
        ForeignKey(
            entity = BubbleLocal::class,
            parentColumns = ["id"],
            childColumns = ["curr_bubble_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = BubbleLocal::class,
            parentColumns = ["id"],
            childColumns = ["link_bubble_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class LinkedBubbleLocal(
    @PrimaryKey(autoGenerate = true) override val id: Int = 0,
    @ColumnInfo(name = "curr_bubble_id") val currBubbleId: Int,
    @ColumnInfo(name = "link_bubble_id") val linkBubbleId: Int,
    @ColumnInfo(name = "is_back") val isBack: Boolean,
    @ColumnInfo(name = "created_at") override var createdAt: Date = Date(),
    @ColumnInfo(name = "updated_at") override var updatedAt: Date = Date(),
) : BaseLocal
