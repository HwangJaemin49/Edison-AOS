package com.umc.edison.presentation.model

import com.umc.edison.domain.model.Bubble
import com.umc.edison.domain.model.ContentType
import com.umc.edison.domain.model.StyleRange

data class BubbleModel(
    val id: Int = 0,
    val title: String? = null,
    val contentBlocks: List<BubbleContentBlock> = listOf(),
    val mainImage: String? = null,
    val labels: List<LabelModel> = listOf(),
    val date: Long = System.currentTimeMillis()
) {
    fun toDomain(): Bubble = Bubble(id, title, contentBlocks.map { it.toDomain() }, mainImage, labels.map { it.toDomain() }, date)

    data class BubbleContentBlock(
        val type: ContentType,
        var content: String,
        var position: Int,
        var contentStyles: MutableList<StyleRange> = mutableListOf()
    ) {
        fun toDomain(): Bubble.BubbleContentBlock = Bubble.BubbleContentBlock(type, content, position)
    }
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

fun Bubble.BubbleContentBlock.toPresentation(): BubbleModel.BubbleContentBlock = BubbleModel.BubbleContentBlock(type, content, position)

fun Bubble.toPresentation(): BubbleModel =
    BubbleModel(id, title, contentBlocks.map { it.toPresentation() }, mainImage, labels.toPresentation(), date)


