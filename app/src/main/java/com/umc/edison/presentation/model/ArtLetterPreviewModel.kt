package com.umc.edison.presentation.model

import com.umc.edison.domain.model.ArtLetterPreview

data class ArtLetterPreviewModel(
    val artLetterId: Int,
    val title: String,
    val thumbnail: String,
    val scraped: Boolean,
    val tags: List<String>,
)

fun ArtLetterPreview.toPresentation(): ArtLetterPreviewModel = ArtLetterPreviewModel(
    artLetterId = artLetterId,
    title = title,
    thumbnail = thumbnail,
    scraped = scraped,
    tags = tags,
)

fun List<ArtLetterPreview>.toPresentation(): List<ArtLetterPreviewModel> {
    return map { it.toPresentation() }
}
