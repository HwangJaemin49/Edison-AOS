package com.umc.edison.data.model.bubble

import com.umc.edison.data.model.DataMapper
import com.umc.edison.domain.model.bubble.ClusteredBubble

data class PositionedBubbleEntity(
    val bubble : BubbleEntity,
    val x: Float,
    val y: Float,
    val group: Int
) : DataMapper<ClusteredBubble> {
    override fun toDomain(): ClusteredBubble = ClusteredBubble(
        bubble = bubble.toDomain(),
        x = x,
        y = y,
        groupId = group
    )
}