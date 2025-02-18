package com.umc.edison.presentation.model

import com.umc.edison.domain.model.EditorPickArtLetter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class EditorPickArtLetterModel(
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
) {
    companion object {
        private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

        val DEFAULT = EditorPickArtLetterModel(
            artletterId = 1,
            title = "제목",
            content = "본문",
            category = "카테고리",
            readTime = 0,
            writer = "작가",
            tags = "태그",
            thumbnail = null,
            likesCnt = 0,
            scrapsCnt = 0,
            createdAt = dateFormat.parse("2025-02-16 15:30:45") ?: Date(),
            updatedAt = dateFormat.parse("2025-02-16 15:30:45") ?: Date(),
            liked = false,
            scraped = false
        )
    }

    fun toDomain(): EditorPickArtLetter {
        return EditorPickArtLetter(
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

fun EditorPickArtLetter.toPresentation(): EditorPickArtLetterModel {
    return EditorPickArtLetterModel(
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

fun List<EditorPickArtLetter>.toPresentation(): List<EditorPickArtLetterModel> {
    return map { it.toPresentation() }
}