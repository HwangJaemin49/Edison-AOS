package com.umc.edison.data.model

import android.util.Log
import com.umc.edison.domain.model.ArtLetter
import java.util.Date

data class ArtletterEntity(
    val artletterId: Int,
    val title: String,
    val thumbnail: String?,
    val likesCnt: Int,
    val scrapsCnt: Int,
    val updatedAt: Date,
    val liked: Boolean,
    val scraped: Boolean
) : DataMapper<ArtLetter> {
    override fun toDomain(): ArtLetter {
        Log.d("Mapping", "ArtLetterEntity 내 toDomain 변환 - id: $artletterId, title: $title")
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

fun ArtLetter.toData(): ArtletterEntity {
    return ArtletterEntity(
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