package com.umc.edison.presentation.label

import androidx.compose.ui.graphics.Color
import com.umc.edison.domain.model.Label

data class LabelListModel(
    val id: Int,
    val name: String,
    val color: Color,
    val bubbleCnt: Int = 0,
) {
    fun toDomain(): Label = Label(
        id = id,
        name = name,
        color = color,
        bubbles = emptyList()
    )
}

fun Label.toLabelListPresentation(): LabelListModel = LabelListModel(id, name, color, bubbles.size)

fun List<Label>.toLabelListPresentation(): List<LabelListModel> = map { it.toLabelListPresentation() }

