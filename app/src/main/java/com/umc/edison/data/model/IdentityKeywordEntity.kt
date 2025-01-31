package com.umc.edison.data.model

import com.umc.edison.data.DataMapper
import com.umc.edison.domain.model.IdentityKeyword

data class IdentityKeywordEntity(
    val question: String,
    val keywords: List<KeywordEntity>
) : DataMapper<IdentityKeyword> {
    override fun toDomain(): IdentityKeyword {
        return IdentityKeyword(
            question = question,
            keywords = keywords.map { it.toDomain() }
        )
    }
}
