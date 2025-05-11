package com.umc.edison.data.model.label

import android.util.Log
import androidx.compose.ui.graphics.Color
import com.umc.edison.data.model.DataMapper
import com.umc.edison.domain.model.label.Label
import java.util.Date

data class LabelEntity(
    val id: String,
    val name: String,
    val color: Color,
    val isDeleted: Boolean = false,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date(),
    val deletedAt: Date? = null,
    val isSynced: Boolean = false
) : DataMapper<Label> {
    override fun toDomain(): Label = Label(
        id = id,
        name = name,
        color = color,
    )
}

fun LabelEntity.same(other: LabelEntity): Boolean {
    Log.d("LabelEntity", "this: $this,\nother: $other")
    return id == other.id &&
        name == other.name &&
        color == other.color &&
        isDeleted == other.isDeleted
}

fun Label.toData(): LabelEntity = LabelEntity(
    id = id,
    name = name,
    color = color,
)

fun List<Label>.toData(): List<LabelEntity> = map { it.toData() }
