package com.umc.edison.data.model

import com.umc.edison.domain.model.ClusteredBubbles

data class ClusteredBubblesEntity(
    val groupId: Int,
    val centerX: Float,
    val centerY: Float,
    val radius: Float,
    val bubbles: List<PositionedBubbleEntity>
) : DataMapper<ClusteredBubbles> {
    private val scale = 40f

    override fun toDomain(): ClusteredBubbles = ClusteredBubbles(
        groupId = groupId,
        centerX = centerX * scale,
        centerY = centerY * scale,
        radius = radius * scale,
        bubbles = bubbles.map { it.toDomain() }
    )
}
