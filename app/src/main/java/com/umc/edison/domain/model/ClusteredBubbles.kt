package com.umc.edison.domain.model

data class ClusteredBubbles(
    val groupId: Int,
    val centerX: Float,
    val centerY: Float,
    val radius: Float,
    val bubbles: List<ClusteredBubblePosition>
)
