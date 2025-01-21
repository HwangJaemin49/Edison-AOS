package com.umc.edison.domain.model

data class Bubble(
    val id: Int,
    val title: String? = null,
    val contentBlocks: List<ContentBlock> = listOf(),
    val mainImage: String? = null,
    val labels: List<Label>,
    val date: Long = System.currentTimeMillis(),
)

data class ContentBlock(
    val type: ContentType,
    var content: String,
    var position: Int,
)

enum class ContentType {
    TEXT,
    IMAGE,
}
