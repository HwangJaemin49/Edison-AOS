package com.umc.edison.data.model

import androidx.compose.ui.graphics.Color
import com.umc.edison.data.DataMapper
import com.umc.edison.domain.model.Label

data class LabelEntity(
    val id: Int,
    val name: String,
    val color: Color,
    var bubbles: List<BubbleEntity>
) : DataMapper<Label> {
    override fun toDomain(): Label = Label(
        id = id,
        name = name,
        color = color,
        bubbles = bubbles.map { it.toDomain() }
    )
}

fun Label.toEntity(): LabelEntity = LabelEntity(
    id = id,
    name = name,
    color = color,
    bubbles = bubbles.map { it.toEntity() }
)

fun List<Label>.toEntity(): List<LabelEntity> = map { it.toEntity() }
