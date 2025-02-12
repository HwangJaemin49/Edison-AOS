package com.umc.edison.presentation.model

import com.umc.edison.domain.model.ArtLetter

data class ArtLetterModel(
    val id: Int,
    val title: String,
    val thumbnail: String,
    val likes: Int,
    val scraps: Int,
) {
    companion object {
        val DEFAULT = ArtLetterModel(
            id = 0,
            title = "제목",
            thumbnail = "", // Default 썸네일?
            likes = 0,
            scraps = 0,
        )
    }

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

fun ArtLetter.toPresentation(): ArtLetterModel {
    return ArtLetterModel(
        id = id,
        title = title,
        thumbnail = thumbnail,
        likes = likes,
        scraps = scraps,
    )
}

fun List<ArtLetter>.toPresentation(): List<ArtLetterModel> {
    return map { it.toPresentation() }
}