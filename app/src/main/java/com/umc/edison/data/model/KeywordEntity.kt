package com.umc.edison.data.model

import com.umc.edison.data.DataMapper
import com.umc.edison.domain.model.Keyword

data class KeywordEntity(
    val id: Int,
    val name: String
) : DataMapper<Keyword> {
    override fun toDomain(): Keyword {
        return Keyword(
            id = id,
            name = name
        )
    }
}
