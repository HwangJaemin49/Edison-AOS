package com.umc.edison.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.umc.edison.data.model.bubble.BubbleEntity
import java.util.Date
import java.util.UUID

@Entity
data class BubbleLocal(
    @PrimaryKey
    @ColumnInfo(name = "id") override val uuid: String = UUID.randomUUID().toString(),
    val title: String?,
    val content: String?,
    @ColumnInfo(name = "main_image") val mainImage: String?,
    @ColumnInfo(name = "is_synced") override var isSynced: Boolean = false,
    @ColumnInfo(name = "is_trashed") var isTrashed: Boolean = false,
    @ColumnInfo(name = "is_deleted") override var isDeleted: Boolean = false,
    @ColumnInfo(name = "created_at") override var createdAt: Date = Date(),
    @ColumnInfo(name = "updated_at") override var updatedAt: Date = Date(),
    @ColumnInfo(name = "deleted_at") override var deletedAt: Date? = null,
) : LocalMapper<BubbleEntity>, BaseSyncLocal {

    override fun toData(): BubbleEntity = BubbleEntity(
        id = uuid,
        title = title,
        content = content,
        mainImage = mainImage,
        labels = emptyList(),
        isDeleted = isDeleted,
        isTrashed = isTrashed,
        createdAt = createdAt,
        updatedAt = updatedAt,
        deletedAt = deletedAt,
        backLinks = emptyList(),
        linkedBubble = null,
        isSynced = isSynced,
    )
}

fun BubbleEntity.toLocal(): BubbleLocal = BubbleLocal(
    uuid = id,
    title = title,
    content = content,
    mainImage = mainImage,
    isSynced = isSynced,
    isTrashed = isTrashed,
    isDeleted = isDeleted,
    createdAt = createdAt,
    updatedAt = updatedAt,
    deletedAt = deletedAt,
)
