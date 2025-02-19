package com.umc.edison.domain.model

class ArtLetterDetail (
    val artLetterId: Int,
    val title: String,
    val content: String,
    val category: String,
    val readTime: Int,
    val writer: String,
    val tags: List<String>,
    val thumbnail: String,
    val likesCnt: Int,
    val liked: Boolean,
    val scraped: Boolean,
)