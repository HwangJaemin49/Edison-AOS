package com.umc.edison.domain.model.bubble

data class ClusteredBubblePosition(
    val bubble: Bubble,
    val x: Float,
    val y: Float,
    val groupId: Int,
)