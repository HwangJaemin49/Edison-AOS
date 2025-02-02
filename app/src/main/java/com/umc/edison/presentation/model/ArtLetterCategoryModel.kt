package com.umc.edison.presentation.model

import com.umc.edison.domain.model.ArtLetterCategory

data class ArtLetterCategoryModel(
    val id: Int,
    val title: String,
    val mainImage: String,
)

fun ArtLetterCategory.toPresentation(): ArtLetterCategoryModel =
    ArtLetterCategoryModel(id, name, thumbnail)
