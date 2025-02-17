package com.umc.edison.presentation.model

import androidx.compose.ui.geometry.Offset
import com.umc.edison.domain.model.ClusteredBubblePosition

data class PositionedBubbleModel(
    val bubble: BubbleModel,
    val position: Offset,
    val groupId: Int
)

fun ClusteredBubblePosition.toPresentation(): PositionedBubbleModel {
    val scale = 700f

    return PositionedBubbleModel(
        bubble = bubble.toPresentation(),
        position = Offset(x * scale, y * scale),
        groupId = groupId
    )
}

fun List<ClusteredBubblePosition>.toPresentation(): List<PositionedBubbleModel> {
    return map { it.toPresentation() }
}
