package com.umc.edison.local.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.umc.edison.data.model.LabelEntity

@Entity
data class LabelLocal(
    val name: String,
    val color: Int,
    var isDeleted: Boolean,
    var isSynced: Boolean,
) : LocalMapper<LabelEntity> {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    override fun toData(): LabelEntity = LabelEntity(
        id = id,
        name = name,
        color = Color(color),
    )
}

fun LabelEntity.toLocal(): LabelLocal = LabelLocal(
    name = name,
    color = color.toArgb(),
    isDeleted = false,
    isSynced = false,
)
