package com.umc.edison.domain.model

import java.util.Date

data class Bubble(
    val id: Int = 0,
    val title: String? = null,
    val contentBlocks: List<BubbleContentBlock> = listOf(),
    val mainImage: String? = null,
    val labels: List<Label>,
    val date: Date = Date(),
) {
    data class BubbleContentBlock(
        val type: ContentType,
        var content: String,
        var position: Int,
    )
}
enum class ContentType {
    TEXT,
    IMAGE,
}
