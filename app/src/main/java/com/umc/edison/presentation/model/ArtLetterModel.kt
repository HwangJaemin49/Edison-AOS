package com.umc.edison.presentation.model

import com.umc.edison.domain.model.ArtLetter

data class ArtLetterModel(
    val artletterId: Int,
    val title: String,
    val thumbnail: String?,
    val likes: Int,
    val scraps: Int,
) {
    companion object {
        val DEFAULT = ArtLetterModel(
            artletterId = 1,
            title = "",
            thumbnail = "",
            likes = 0,
            scraps = 0,
        )
    }

    fun toDomain(): ArtLetter {
        return ArtLetter(
            artletterId = artletterId,
            title = title,
            thumbnail = thumbnail,
            likes = likes,
            scraps = scraps,
        )
    }
}

fun ArtLetter.toPresentation(): ArtLetterModel {
    return ArtLetterModel(
        artletterId = artletterId,
        title = title,
        thumbnail = thumbnail,
        likes = likes,
        scraps = scraps,
    )
}

fun List<ArtLetter>.toPresentation(): List<ArtLetterModel> {
    return map { it.toPresentation() }
}