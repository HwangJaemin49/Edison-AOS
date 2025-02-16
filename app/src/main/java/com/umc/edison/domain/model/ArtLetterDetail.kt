package com.umc.edison.domain.model


class ArtLetterDetail (
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
    val createdAt: String,
    val updatedAt: String,
    val liked: Boolean,
    val scraped: Boolean,
)