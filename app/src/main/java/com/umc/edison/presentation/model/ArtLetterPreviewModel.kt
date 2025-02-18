package com.umc.edison.presentation.model

import com.umc.edison.domain.model.ArtLetterPreview

data class ArtLetterPreviewModel(
    val artLetterId: Int,
    val title: String,
    val thumbnail: String,
    val scraped: Boolean
)

fun ArtLetterPreview.toPresentation(): ArtLetterPreviewModel = ArtLetterPreviewModel(
    artLetterId = artLetterId,
    title = title,
    thumbnail = thumbnail,
    scraped = scraped
)

fun List<ArtLetterPreview>.toPresentation(): List<ArtLetterPreviewModel> {
    return map { it.toPresentation() }
}