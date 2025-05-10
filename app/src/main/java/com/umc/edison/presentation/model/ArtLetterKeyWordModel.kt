package com.umc.edison.presentation.model

import com.umc.edison.domain.model.ArtLetterKeyWord

data class ArtLetterKeyWordModel(
    val artLetterId: Int,
    val keyword: String
)

fun ArtLetterKeyWord.toPresentation(): ArtLetterKeyWordModel = ArtLetterKeyWordModel(
    artLetterId = artletterId,
    keyword = keyword
)

fun List<ArtLetterKeyWord>.toPresentation(): List<ArtLetterKeyWordModel> {
    return map { it.toPresentation() }
}
