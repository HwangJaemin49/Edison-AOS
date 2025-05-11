package com.umc.edison.data.model

import com.umc.edison.data.model.artLetter.toData
import com.umc.edison.domain.model.identity.IdentityCategory

data class IdentityEntity(
    val categoryNumber: String,
    val keywords: List<KeywordEntity>,
    val options: List<KeywordEntity>,
) : DataMapper<Identity> {
    override fun toDomain(): Identity {
        return Identity(
            category = IdentityCategoryMapper.entries.first { it.categoryNumber == categoryNumber }.category,
            selectedKeywords = keywords.map { it.toDomain() },
            options = options.map { it.toDomain() }
        )
    }
}

fun Identity.toData(): IdentityEntity {
    return IdentityEntity(
        categoryNumber = IdentityCategoryMapper.entries.first { it.category == category }.categoryNumber,
        keywords = selectedKeywords.map { it.toData() },
        options = options.map { it.toData() }
    )
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
