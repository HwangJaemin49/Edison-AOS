package com.umc.edison.presentation.model

import androidx.compose.ui.graphics.Color
import com.umc.edison.data.toDomain
import com.umc.edison.domain.model.Bubble
import com.umc.edison.domain.model.ContentType
import com.umc.edison.domain.model.Label

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
        var position: Int
    ) {
        fun toDomain(): Bubble.BubbleContentBlock = Bubble.BubbleContentBlock(type, content, position)
    }

    data class LabelModel(
        val id: Int,
        val name: String,
        val color: Color
    ) {
        fun toDomain(): Label = Label(
            id = id,
            name = name,
            color = color,
            bubbles = emptyList()
        )
    }
}

fun Bubble.BubbleContentBlock.toPresentation(): BubbleModel.BubbleContentBlock = BubbleModel.BubbleContentBlock(type, content, position)

fun Bubble.toPresentation(): BubbleModel =
    BubbleModel(id, title, contentBlocks.map { it.toPresentation() }, mainImage, labels.map { it.toDomain() }, date)


