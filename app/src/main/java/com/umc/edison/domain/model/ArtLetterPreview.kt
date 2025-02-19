package com.umc.edison.domain.model

data class ArtLetterPreview(
    val artLetterId: Int,
    val title: String,
    val thumbnail: String,
    val scraped: Boolean,
    val tags: List<String>,
)
