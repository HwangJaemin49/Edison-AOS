package com.umc.edison.domain.model

import java.util.Date

data class ArtLetter(
    val artletterId: Int,
    val title: String,
    val thumbnail: String?,
    val likesCnt: Int,
    val scrapsCnt: Int,
    val updatedAt: Date,
    val liked: Boolean,
    val scraped: Boolean
)
