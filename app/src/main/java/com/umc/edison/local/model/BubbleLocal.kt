package com.umc.edison.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.umc.edison.data.model.BubbleEntity

@Entity
data class BubbleLocal(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    val title: String?,
    val content: String?,
    @ColumnInfo(name = "main_image") val mainImage: String?,
    @ColumnInfo(name = "is_synced") override var isSynced: Boolean = false,
    @ColumnInfo(name = "is_deleted") override var isDeleted: Boolean = false,
    @ColumnInfo(name = "created_at") override var createdAt: Long? = null,
    @ColumnInfo(name = "updated_at") override var updatedAt: Long? = null,
    @ColumnInfo(name = "deleted_at") override var deletedAt: Long? = null,
) : LocalMapper<BubbleEntity>, BaseSyncLocal {

    override fun toData(): BubbleEntity = BubbleEntity(
        id = id,
        title = title,
        content = content,
        mainImage = mainImage,
        labels = emptyList(),
        date = updatedAt ?: System.currentTimeMillis()
    )
}
