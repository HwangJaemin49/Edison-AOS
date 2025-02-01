package com.umc.edison.data.model

import com.umc.edison.domain.model.IdentityCategory
import com.umc.edison.domain.model.IdentityKeyword

data class IdentityKeywordEntity(
    val categoryNumber: String,
    val keywords: List<KeywordEntity>,
    val options: List<KeywordEntity>,
) : DataMapper<IdentityKeyword> {
    override fun toDomain(): IdentityKeyword {
        return IdentityKeyword(
            category = IdentityCategoryMapper.entries.first { it.categoryNumber == categoryNumber }.category,
            selectedKeywords = keywords.map { it.toDomain() },
            options = options.map { it.toDomain() }
        )
    }
}

enum class IdentityCategoryMapper(
    val categoryNumber: String,
    val category: IdentityCategory
) {
    EXPLAIN(
        categoryNumber = "CATEGORY1",
        category = IdentityCategory.EXPLAIN
    ),

    FIELD(
        categoryNumber = "CATEGORY2",
        category = IdentityCategory.FIELD
    ),

    ENVIRONMENT(
        categoryNumber = "CATEGORY3",
        category = IdentityCategory.ENVIRONMENT
    )
}
