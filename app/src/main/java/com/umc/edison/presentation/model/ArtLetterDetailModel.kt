package com.umc.edison.presentation.model

import com.umc.edison.domain.model.ArtLetterDetail

data class ArtLetterDetailModel(
    val artLetterId: Int,
    val title: String,
    val content: String,
    val category: String,
    val readTime: Int,
    val writer: String,
    val tags: String,
    val thumbnail: String,
    val likesCnt: Int,
    val liked: Boolean,
    val scraped: Boolean,
)

fun ArtLetterDetail.toPresentation(): ArtLetterDetailModel = ArtLetterDetailModel(
    artLetterId = artLetterId,
    title = title,
    content = content,
    category = category,
    readTime = readTime,
    writer = writer,
    tags = tags,
    thumbnail = thumbnail,
    likesCnt = likesCnt,
    liked = liked,
    scraped = scraped
)