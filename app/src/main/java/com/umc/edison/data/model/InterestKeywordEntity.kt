package com.umc.edison.data.model

import com.umc.edison.data.DataMapper
import com.umc.edison.domain.model.InterestKeyword

data class InterestKeywordEntity(
    val question: String,
    val keywords: List<KeywordEntity>
) : DataMapper<InterestKeyword> {
    override fun toDomain(): InterestKeyword {
        return InterestKeyword(
            question = question,
            keywords = keywords.map { it.toDomain() }
        )
    }
}
