package com.umc.edison.local.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.umc.edison.data.model.LabelEntity
import java.util.Date

@Entity
data class LabelLocal(
    @PrimaryKey(autoGenerate = true) override val id: Int = 0,
    val name: String,
    val color: Int,
    @ColumnInfo(name = "is_synced") override var isSynced: Boolean = false,
    @ColumnInfo(name = "is_deleted") override var isDeleted: Boolean = false,
    @ColumnInfo(name = "created_at") override var createdAt: Date? = null,
    @ColumnInfo(name = "updated_at") override var updatedAt: Date? = null,
    @ColumnInfo(name = "deleted_at") override var deletedAt: Date? = null,
) : LocalMapper<LabelEntity>, BaseSyncLocal {
    override fun toData(): LabelEntity = LabelEntity(
        id = id,
        name = name,
        color = Color(color),
        bubbles = emptyList(),
    )
}

fun LabelEntity.toLocal(): LabelLocal = LabelLocal(
    id = id,
    name = name,
    color = color.toArgb(),
)
