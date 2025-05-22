package com.umc.edison.presentation.model

import com.umc.edison.domain.model.artLetter.ArtLetter

data class ArtLetterPreviewModel(
    val artLetterId: Int,
    val title: String,
    val thumbnail: String,
    val scraped: Boolean,
    val tags: List<String>,
)

fun ArtLetter.toPreviewPresentation(): ArtLetterPreviewModel = ArtLetterPreviewModel(
    artLetterId = artLetterId,
    title = title,
    thumbnail = thumbnail,
    scraped = scraped,
    tags = tags,
)

fun List<ArtLetter>.toPreviewPresentation(): List<ArtLetterPreviewModel> {
    return map { it.toPreviewPresentation() }
}
