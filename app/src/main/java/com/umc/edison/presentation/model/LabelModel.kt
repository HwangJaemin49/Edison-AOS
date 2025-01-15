package com.umc.edison.presentation.model

import androidx.compose.ui.graphics.Color
import com.umc.edison.domain.model.Label

data class LabelModel(
    val id: Int? = null,
    val name: String,
    val color: Color,
    val bubbleCnt: Int = 0,
) {
    fun toDomain(): Label = Label(id, name, color, bubbleCnt)
}

fun Label.toPresentation(): LabelModel = LabelModel(id, name, color, bubbleCnt)

fun List<Label>.toPresentation(): List<LabelModel> = map { it.toPresentation() }