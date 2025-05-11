package com.umc.edison.data.model.artLetter

import com.umc.edison.data.model.DataMapper
import com.umc.edison.domain.model.artLetter.ArtLetterKeyWord

data class ArtLetterKeyWordEntity (
    val artLetterId: Int,
    val keyword: String,
) : DataMapper<ArtLetterKeyWord> {
    override fun toDomain(): ArtLetterKeyWord = ArtLetterKeyWord(
        artLetterId = artLetterId,
        keyword = keyword,
    )
}

fun ArtLetterKeyWord.toData(): ArtLetterKeyWordEntity = ArtLetterKeyWordEntity(
    artLetterId = artLetterId,
    keyword = keyword
)