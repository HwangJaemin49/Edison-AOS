package com.umc.edison.data.model

import com.umc.edison.domain.model.ArtLetterKeyWord

data class ArtLetterKeyWordEntity (
    val artletterId: Int,
    val keyword: String,
) : DataMapper<ArtLetterKeyWord> {
    override fun toDomain(): ArtLetterKeyWord = ArtLetterKeyWord(
        artletterId = artletterId,
        keyword = keyword,
    )
}

fun ArtLetterKeyWord.toData(): ArtLetterKeyWordEntity = ArtLetterKeyWordEntity(
    artletterId = artletterId,
    keyword = keyword
)