package com.umc.edison.data.model

import com.umc.edison.domain.model.ClusteredBubblePosition

data class PositionedBubbleEntity(
    val bubble : BubbleEntity,
    val x: Float,
    val y: Float,
    val group: Int
) : DataMapper<ClusteredBubblePosition> {
    override fun toDomain(): ClusteredBubblePosition = ClusteredBubblePosition(
        bubble = bubble.toDomain(),
        x = x,
        y = y,
    )
}
