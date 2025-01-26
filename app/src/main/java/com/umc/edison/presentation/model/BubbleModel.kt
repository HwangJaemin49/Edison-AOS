package com.umc.edison.presentation.model

import com.umc.edison.domain.model.Bubble
import java.util.Date

data class BubbleModel(
    val id: Int = 0,
    val title: String? = null,
    val contentBlocks: List<ContentBlockModel> = listOf(),
    val mainImage: String? = null,
    val labels: List<LabelModel> = listOf(),
    val date: Date = Date()
) {
    fun toDomain(): Bubble = Bubble(id, title, contentBlocks.map { it.toDomain() }, mainImage, labels.map { it.toDomain() }, date)
}

fun Bubble.toPresentation(): BubbleModel =
    BubbleModel(id, title, contentBlocks.toPresentation(), mainImage, labels.toPresentation(), date)

fun List<Bubble>.toPresentation(): List<BubbleModel> = map { it.toPresentation() }
