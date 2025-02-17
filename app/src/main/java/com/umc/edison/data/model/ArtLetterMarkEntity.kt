package com.umc.edison.data.model


import com.umc.edison.domain.model.ArtLetterMark

data class ArtLetterMarkEntity(
    val artletterId: Int,
    val likesCnt: Int,
    val liked: Boolean
) : DataMapper<ArtLetterMark> {
    override fun toDomain(): ArtLetterMark {
        return ArtLetterMark(
            artletterId = artletterId,
            likesCnt = likesCnt,
            liked = liked
        )
    }
}

fun ArtLetterMark.toData(): ArtLetterMarkEntity {
    return ArtLetterMarkEntity(
        artletterId = artletterId,
        likesCnt = likesCnt,
        liked = liked
    )
}