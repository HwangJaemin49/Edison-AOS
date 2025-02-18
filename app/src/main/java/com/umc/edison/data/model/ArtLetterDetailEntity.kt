package com.umc.edison.data.model

import com.umc.edison.domain.model.ArtLetterDetail

data class ArtLetterDetailEntity(
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
    val scraped: Boolean
) : DataMapper<ArtLetterDetail> {
    override fun toDomain(): ArtLetterDetail = ArtLetterDetail(
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
}

fun ArtLetterDetail.toData(): ArtLetterDetailEntity = ArtLetterDetailEntity(
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