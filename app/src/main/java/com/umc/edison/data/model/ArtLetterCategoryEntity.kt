package com.umc.edison.data.model

import com.umc.edison.domain.model.ArtLetterCategory

data class ArtLetterCategoryEntity(
    val name: String,
    val thumbnail: String? = null,
) : DataMapper<ArtLetterCategory> {
    override fun toDomain(): ArtLetterCategory {
        return ArtLetterCategory(
            name = name,
            thumbnail = thumbnail,
        )
    }
}

fun ArtLetterCategory.toData(): ArtLetterCategoryEntity {
    return ArtLetterCategoryEntity(
        name = name,
        thumbnail = thumbnail,
    )
}
