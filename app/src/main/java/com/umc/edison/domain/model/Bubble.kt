package com.umc.edison.domain.model

import java.util.Date

data class Bubble(
    val id: Int = 0,
    val title: String? = null,
    val contentBlocks: List<ContentBlock> = listOf(),
    val mainImage: String? = null,
    var labels: List<Label>,
    val backLinks: List<Bubble> = listOf(),
    val linkedBubble: Bubble? = null,
    val date: Date = Date(),
)

enum class ContentType {
    TEXT,
    IMAGE,
}
