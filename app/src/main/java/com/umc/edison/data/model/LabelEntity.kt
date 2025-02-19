package com.umc.edison.data.model

import android.util.Log
import androidx.compose.ui.graphics.Color
import com.umc.edison.domain.model.Label
import java.util.Date

data class LabelEntity(
    val id: Int,
    val name: String,
    val color: Color,
    var bubbles: List<BubbleEntity> = emptyList(),
    val isDeleted: Boolean = false,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date(),
    val deletedAt: Date? = null
) : DataMapper<Label> {
    override fun toDomain(): Label = Label(
        id = id,
        name = name,
        color = color,
        bubbles = bubbles.map { it.toDomain() }
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
    bubbles = bubbles.toData()
)

fun List<Label>.toData(): List<LabelEntity> = map { it.toData() }
