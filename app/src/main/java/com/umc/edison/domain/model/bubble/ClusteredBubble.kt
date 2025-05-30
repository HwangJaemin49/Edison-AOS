package com.umc.edison.domain.model.bubble

data class ClusteredBubble(
    val bubble: Bubble,
    val x: Float,
    val y: Float,
    val groupId: Int,
)