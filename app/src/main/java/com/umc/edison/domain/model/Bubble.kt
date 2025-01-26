package com.umc.edison.domain.model

import java.util.Date

data class Bubble(
    val id: Int = 0,
    val title: String? = null,
    val contentBlocks: List<ContentBlock> = listOf(),
    val mainImage: String? = null,
    val labels: List<Label>,
    val date: Date = Date(),
)

enum class ContentType {
    TEXT,
    IMAGE,
}
