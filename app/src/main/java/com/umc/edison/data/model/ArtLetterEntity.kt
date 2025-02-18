package com.umc.edison.data.model

import android.util.Log
import com.umc.edison.domain.model.ArtLetter

data class ArtletterEntity(
    val artletterId: Int,
    val title: String,
    val thumbnail: String?,
    val likes: Int,
    val scraps: Int,
) : DataMapper<ArtLetter> {
    override fun toDomain(): ArtLetter {
        Log.d("Mapping", "ArtLetterEntity 내 toDomain 변환 - id: $artletterId, title: $title")
        return ArtLetter(
            artletterId = artletterId,
            title = title,
            thumbnail = thumbnail,
            likes = likes,
            scraps = scraps,
        )
    }

}

fun ArtLetter.toData(): ArtletterEntity {
    return ArtletterEntity(
        artletterId = artletterId,
        title = title,
        thumbnail = thumbnail,
        likes = likes,
        scraps = scraps,
    )
}