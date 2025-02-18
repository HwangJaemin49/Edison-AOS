package com.umc.edison.data.model

import com.umc.edison.domain.model.ArtLetterPreview

data class ArtLetterPreviewEntity(
    val artLetterId: Int,
    val title: String,
    val thumbnail: String,
    val scraped: Boolean,
    val tags: List<String>,
) : DataMapper<ArtLetterPreview> {
    override fun toDomain(): ArtLetterPreview = ArtLetterPreview(
        artLetterId = artLetterId,
        title = title,
        thumbnail = thumbnail,
        scraped = scraped,
        tags = tags,
    )
}

fun ArtLetterPreview.toData(): ArtLetterPreviewEntity = ArtLetterPreviewEntity(
    artLetterId = artLetterId,
    title = title,
    thumbnail = thumbnail,
    scraped = scraped,
    tags = tags,
)