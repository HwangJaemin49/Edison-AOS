package com.umc.edison.data.model

import com.umc.edison.domain.model.InterestCategory
import com.umc.edison.domain.model.InterestKeyword

data class InterestKeywordEntity(
    val categoryNumber: String,
    val keywords: List<KeywordEntity>,
    val options: List<KeywordEntity>,
) : DataMapper<InterestKeyword> {
    override fun toDomain(): InterestKeyword {
        return InterestKeyword(
            category = InterestCategoryMapper.entries.first { it.categoryNumber == categoryNumber }.category,
            keywords = keywords.map { it.toDomain() },
            options = options.map { it.toDomain() }
        )
    }
}

enum class InterestCategoryMapper(
    val categoryNumber: String,
    val category: InterestCategory
) {
    INSPIRATION(
        categoryNumber = "CATEGORY1",
        category = InterestCategory.INSPIRATION
    ),
}
