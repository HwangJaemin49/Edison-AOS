package com.umc.edison.data.model

import com.umc.edison.domain.model.ArtLetter

data class ArtletterEntity(
    val id: Int,
    val title: String,
    val thumbnail: String,
    val likes: Int,
    val scraps: Int,
) : DataMapper<ArtLetter> {
    override fun toDomain(): ArtLetter {
        return ArtLetter(
            id = id,
            title = title,
            thumbnail = thumbnail,
            likes = likes,
            scraps = scraps,
        )
    }
}

fun ArtLetter.toData(): ArtletterEntity {
    return ArtletterEntity(
        id = id,
        title = title,
        thumbnail = thumbnail,
        likes = likes,
        scraps = scraps,
    )
}