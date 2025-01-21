package com.umc.edison.presentation.model

import com.umc.edison.domain.model.Bubble
import com.umc.edison.domain.model.ContentBlock
import com.umc.edison.domain.model.ContentType

data class BubbleModel(
    val id: Int = 0,
    val title: String? = null,
    val contentBlocks: List<ContentBlockModel> = listOf(),
    val mainImage: String? = null,
    val labels: List<LabelModel> = listOf(),
    val date: Long = System.currentTimeMillis()
) {
    fun toDomain(): Bubble = Bubble(id, title, contentBlocks.map { it.toDomain() }, mainImage, labels.map { it.toDomain() }, date)
}

data class ContentBlockModel(
    val type: ContentType,
    var content: String,
    var position: Int
) {
    fun toDomain(): ContentBlock = ContentBlock(type, content, position)
}

fun ContentBlock.toPresentation(): ContentBlockModel = ContentBlockModel(type, content, position)

fun List<ContentBlock>.toPresentation(): List<ContentBlockModel> = map { it.toPresentation() }

fun Bubble.toPresentation(): BubbleModel =
    BubbleModel(id, title, contentBlocks.toPresentation(), mainImage, labels.toPresentation(), date)

fun List<Bubble>.toPresentation(): List<BubbleModel> = map { it.toPresentation() }
