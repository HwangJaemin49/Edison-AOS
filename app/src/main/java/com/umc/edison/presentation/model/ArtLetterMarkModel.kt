package com.umc.edison.presentation.model


import com.umc.edison.domain.model.ArtLetterMark

data class ArtLetterMarkModel (
    val artletterId: Int,
    val likesCnt: Int,
    val liked: Boolean,
) {
    companion object {
        val DEFAULT = ArtLetterMarkModel(
            artletterId = 1,
            likesCnt = 0,
            liked = false,
        )
    }

    fun toDomain(): ArtLetterMark {
        return ArtLetterMark(
            artletterId = artletterId,
            likesCnt = likesCnt,
            liked = liked,
        )
    }
}

fun ArtLetterMark.toPresentation(): ArtLetterMarkModel {
    return ArtLetterMarkModel(
        artletterId = artletterId,
        likesCnt = likesCnt,
        liked = liked,
    )
}
