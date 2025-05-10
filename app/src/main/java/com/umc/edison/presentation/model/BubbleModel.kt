package com.umc.edison.presentation.model

import com.umc.edison.domain.model.Bubble
import java.util.Date

data class BubbleModel(
    val id: Int,
    val title: String?,
    val contentBlocks: List<ContentBlockModel>,
    val mainImage: String?,
    val labels: List<LabelModel>,
    val backLinks: List<BubbleModel>,
    val linkedBubble: BubbleModel?,
    val date: Date
) {
    fun toDomain(): Bubble = Bubble(
        id,
        title,
        contentBlocks.map { it.toDomain() },
        mainImage,
        labels.map { it.toDomain() },
        backLinks.map { it.toDomain() },
        linkedBubble?.toDomain(),
        date
    )

    companion object {
        val DEFAULT = BubbleModel(
            id = 0,
            title = null,
            contentBlocks = emptyList(),
            mainImage = null,
            labels = emptyList(),
            backLinks = emptyList(),
            linkedBubble = null,
            date = Date()
        )
    }
}

fun Bubble.toPresentation(): BubbleModel =
    BubbleModel(
        id,
        title,
        contentBlocks.toPresentation(),
        mainImage,
        labels.toPresentation(),
        backLinks.toPresentation(),
        linkedBubble?.toPresentation(),
        date
    )

fun List<Bubble>.toPresentation(): List<BubbleModel> = map { it.toPresentation() }
