package com.umc.edison.domain.model

import java.util.Date

class EditorPickArtLetter (
    val artletterId: Int,
    val title: String,
    val content: String,
    val category: String,
    val readTime: Int,
    val writer: String,
    val tags: String,
    val thumbnail: String?,
    val likesCnt: Int,
    val scrapsCnt: Int,
    val createdAt: Date,
    val updatedAt: Date,
    val liked: Boolean,
    val scraped: Boolean,
)