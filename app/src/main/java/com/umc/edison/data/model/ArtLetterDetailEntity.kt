package com.umc.edison.data.model


import com.umc.edison.domain.model.ArtLetterDetail
import java.util.Date

data class ArtLetterDetailEntity(
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
    val scraped: Boolean
) : DataMapper<ArtLetterDetail> {
    override fun toDomain(): ArtLetterDetail {
        return ArtLetterDetail(
            artletterId = artletterId,
            title = title,
            content = content,
            category = category,
            readTime = readTime,
            writer = writer,
            tags = tags,
            thumbnail = thumbnail,
            likesCnt = likesCnt,
            scrapsCnt = scrapsCnt,
            createdAt = createdAt,
            updatedAt = updatedAt,
            liked = liked,
            scraped = scraped
        )
    }
}

fun ArtLetterDetail.toData(): ArtLetterDetailEntity {
    return ArtLetterDetailEntity(
        artletterId = artletterId,
        title = title,
        content = content,
        category = category,
        readTime = readTime,
        writer = writer,
        tags = tags,
        thumbnail = thumbnail,
        likesCnt = likesCnt,
        scrapsCnt = scrapsCnt,
        createdAt = createdAt,
        updatedAt = updatedAt,
        liked = liked,
        scraped = scraped
    )
}