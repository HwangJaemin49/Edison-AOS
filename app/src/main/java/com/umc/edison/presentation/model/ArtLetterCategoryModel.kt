package com.umc.edison.presentation.model

import com.umc.edison.domain.model.artLetter.ArtLetter

data class ArtLetterCategoryModel(
    val title: String,
    val mainImage: String?,
)

fun ArtLetter.toCategoryPresentation(): ArtLetterCategoryModel = ArtLetterCategoryModel(
    title = category,
    mainImage = thumbnail,
)
