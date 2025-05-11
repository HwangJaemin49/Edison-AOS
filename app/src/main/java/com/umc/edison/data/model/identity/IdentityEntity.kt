package com.umc.edison.data.model.identity

import com.umc.edison.data.model.DataMapper
import com.umc.edison.domain.model.identity.Identity
import com.umc.edison.domain.model.identity.IdentityCategory

data class IdentityEntity(
    val categoryNumber: String,
    val selectedKeywords: List<KeywordEntity>,
    val keywords: List<KeywordEntity>,
) : DataMapper<Identity> {
    override fun toDomain(): Identity {
        return Identity(
            category = IdentityCategoryMapper.entries.first { it.categoryNumber == categoryNumber }.category,
            selectedKeywords = selectedKeywords.map { it.toDomain() },
            keywords = keywords.map { it.toDomain() }
        )
    }
}

fun Identity.toData(): IdentityEntity {
    return IdentityEntity(
        categoryNumber = IdentityCategoryMapper.entries.first { it.category == category }.categoryNumber,
        selectedKeywords = selectedKeywords.map { it.toData() },
        keywords = keywords.map { it.toData() }
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
    ),

    INSPIRATION(
        categoryNumber = "CATEGORY4",
        category = IdentityCategory.INSPIRATION
    ),
}
