package com.umc.edison.data.model.artLetter

import com.umc.edison.data.model.DataMapper
import com.umc.edison.domain.model.artLetter.ArtLetter

data class ArtLetterPreviewEntity(
    val artLetterId: Int,
    val title: String,
    val thumbnail: String,
    val scraped: Boolean,
    val tags: List<String>,
) : DataMapper<ArtLetter> {
    override fun toDomain(): ArtLetter = ArtLetter.DEFAULT.copy(
        artLetterId = artLetterId,
        title = title,
        thumbnail = thumbnail,
        scraped = scraped,
        tags = tags,
    )
}
