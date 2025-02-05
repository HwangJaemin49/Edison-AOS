package com.umc.edison.presentation.model

import androidx.compose.ui.graphics.Color
import com.umc.edison.domain.model.Label
import com.umc.edison.ui.theme.Gray300

data class LabelModel(
    val id: Int,
    val name: String,
    val color: Color,
    val bubbles: List<BubbleModel>
) {
    fun toDomain(): Label = Label(
        id = id,
        name = name,
        color = color,
        bubbles = bubbles.map { it.toDomain() }
    )

    companion object {
        val DEFAULT = LabelModel(
            id = 0,
            name = "",
            color = Gray300,
            bubbles = emptyList()
        )
    }
}

fun Label.toPresentation(): LabelModel = LabelModel(id, name, color, bubbles.toPresentation())

fun List<Label>.toPresentation(): List<LabelModel> = map { it.toPresentation() }
