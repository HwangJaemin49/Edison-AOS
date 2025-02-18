package com.umc.edison.presentation.model

import com.umc.edison.domain.model.ArtLetter
import com.umc.edison.presentation.model.ArtLetterDetailModel.Companion
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class ArtLetterModel(
    val artletterId: Int,
    val title: String,
    val thumbnail: String?,
    val likesCnt: Int,
    val scrapsCnt: Int,
    val updatedAt: Date,
    val liked: Boolean,
    val scraped: Boolean
) {
    companion object {
        private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val DEFAULT = ArtLetterModel(
            artletterId = 1,
            title = "",
            thumbnail = "",
            likesCnt = 0,
            scrapsCnt = 0,
            updatedAt = ArtLetterDetailModel.dateFormat.parse("2025-02-16 15:30:45") ?: Date(),
            liked = false,
            scraped = false
        )
    }

    fun toDomain(): ArtLetter {
        return ArtLetter(
            artletterId = artletterId,
            title = title,
            thumbnail = thumbnail,
            likesCnt = likesCnt,
            scrapsCnt = scrapsCnt,
            updatedAt = updatedAt,
            liked = liked,
            scraped = scraped
        )
    }
}

fun ArtLetter.toPresentation(): ArtLetterModel {
    return ArtLetterModel(
        artletterId = artletterId,
        title = title,
        thumbnail = thumbnail,
        likesCnt = likesCnt,
        scrapsCnt = scrapsCnt,
        updatedAt = updatedAt,
        liked = liked,
        scraped = scraped
    )
}

fun List<ArtLetter>.toPresentation(): List<ArtLetterModel> {
    return map { it.toPresentation() }
}