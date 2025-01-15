package com.umc.edison.data.model

import androidx.compose.ui.graphics.Color
import com.umc.edison.data.DataMapper
import com.umc.edison.domain.model.Label

data class LabelEntity(
    val id: Int = 0,
    val name: String,
    val color: Color,
    var bubbleCnt: Int = 0,
) : DataMapper<Label> {
    override fun toDomain(): Label = Label(
        id = id,
        name = name,
        color = color,
        bubbleCnt = bubbleCnt
    )
}

fun Label.toEntity(): LabelEntity = LabelEntity(
    name = name,
    color = color,
    bubbleCnt = bubbleCnt
)

fun List<Label>.toEntity(): List<LabelEntity> = map { it.toEntity() }
