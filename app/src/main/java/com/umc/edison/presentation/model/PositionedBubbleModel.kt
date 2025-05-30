package com.umc.edison.presentation.model

import androidx.compose.ui.geometry.Offset
import com.umc.edison.domain.model.bubble.ClusteredBubble

data class PositionedBubbleModel(
    val bubble: BubbleModel,
    val position: Offset,
    val groupId: Int
)

fun ClusteredBubble.toPresentation(): PositionedBubbleModel {
    val scale = 400f

    return PositionedBubbleModel(
        bubble = bubble.toPresentation(),
        position = Offset(x * scale, y * scale),
        groupId = groupId
    )
}

fun List<ClusteredBubble>.toPresentation(): List<PositionedBubbleModel> {
    return map { it.toPresentation() }
}
