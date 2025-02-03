package com.umc.edison.data.model

import com.umc.edison.domain.model.ArtLetterCategory

data class ArtLetterCategoryEntity(
    val id: Int,
    val name: String,
    val thumbnail: String,
) : DataMapper<ArtLetterCategory> {
    override fun toDomain(): ArtLetterCategory {
        return ArtLetterCategory(
            id = id,
            name = name,
            thumbnail = thumbnail,
        )
    }
}
