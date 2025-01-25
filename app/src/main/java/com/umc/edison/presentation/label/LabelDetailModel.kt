package com.umc.edison.presentation.label

import androidx.compose.ui.graphics.Color
import com.umc.edison.domain.model.Label
import com.umc.edison.presentation.model.BubbleModel
import com.umc.edison.presentation.model.toPresentation

data class LabelDetailModel(
    val id: Int,
    val labelName: String,
    val labelColor: Color,
    val bubbles: List<BubbleModel>
) {
    fun toDomain(): Label = Label(
        id = id,
        name = labelName,
        color = labelColor,
        bubbles = bubbles.map { it.toDomain() }
    )
}

fun Label.toLabelDetailPresentation(): LabelDetailModel = LabelDetailModel(id, name, color, bubbles.map { it.toPresentation() })

fun List<Label>.toLabelDetailPresentation(): List<LabelDetailModel> = map { it.toLabelDetailPresentation() }