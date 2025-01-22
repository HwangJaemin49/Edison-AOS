package com.umc.edison.domain.model

data class Bubble(
    val id: Int,
    val title: String? = null,
    val contentBlocks: List<BubbleContentBlock> = listOf(),
    val mainImage: String? = null,
    val labels: List<Label>,
    val date: Long = System.currentTimeMillis(),
) {
    data class BubbleContentBlock(
        val type: ContentType,
        var content: String,
        var position: Int,
        var contentStyles: MutableList<StyleRange> = mutableListOf()

    )
}
enum class ContentType {
    TEXT,
    IMAGE,
}

data class StyleRange(
    val style: Style, // 스타일 종류 (BOLD, ITALICS 등)
    val range: IntRange // 스타일 적용 범위
)

enum class Style {
    BOLD,
    ITALICS,
    UNDERLINE,
    HIGHLIGHT
}

