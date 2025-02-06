package com.umc.edison.presentation.model

import com.umc.edison.domain.model.ArtLetter

data class ArtLetterModel(
    val id: Int,
    val title: String,
    val thumbnail: String,
    val likes: Int,
    val scraps: Int,
) {
    fun toDomain(): ArtLetter {
        return ArtLetter(
            id = id,
            title = title,
            thumbnail = thumbnail,
            likes = likes,
            scraps = scraps,
        )
    }
}