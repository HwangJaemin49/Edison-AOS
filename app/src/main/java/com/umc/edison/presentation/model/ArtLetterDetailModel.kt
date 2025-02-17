package com.umc.edison.presentation.model


import com.umc.edison.domain.model.ArtLetterDetail
import java.util.Date

data class ArtLetterDetailModel(
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
) {
    companion object {
        val DEFAULT = ArtLetterDetailModel(
            artletterId = 1,
            title = "제목",
            content = "본문",
            category = "카테고리",
            readTime = 0,
            writer = "작가",
            tags = "태그",
            thumbnail = null, // Default 썸네일?
            likesCnt = 0,
            scrapsCnt = 0,
            createdAt = "20250216",
            updatedAt = "20250216",
            liked = false,
            scraped = false
        )
    }

    fun toDomain(): ArtLetterDetail {
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

fun ArtLetterDetail.toPresentation(): ArtLetterDetailModel {
    return ArtLetterDetailModel(
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
