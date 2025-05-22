package com.umc.edison.data.model.identity

import com.umc.edison.data.model.DataMapper
import com.umc.edison.domain.model.identity.Identity
import com.umc.edison.domain.model.identity.IdentityCategory

data class IdentityEntity(
    val category: IdentityCategoryEntity,
    val selectedKeywords: List<IdentityKeywordEntity>,
    val keywords: List<IdentityKeywordEntity>,
) : DataMapper<Identity> {
    override fun toDomain(): Identity {
        return Identity(
            category = category.toDomain(),
            selectedKeywords = selectedKeywords.map { it.toDomain() },
            keywords = keywords.map { it.toDomain() }
        )
    }
}

fun Identity.toData(): IdentityEntity {
    return IdentityEntity(
        category = category.toData(),
        selectedKeywords = selectedKeywords.map { it.toData() },
        keywords = keywords.map { it.toData() }
    )
}

sealed class IdentityCategoryEntity(
    val categoryNumber: String,
) : DataMapper<IdentityCategory> {
    data object EXPLAIN : IdentityCategoryEntity(
        categoryNumber = "CATEGORY1"
    ) {
        override fun toDomain(): IdentityCategory = IdentityCategory.EXPLAIN
    }

    data object FIELD : IdentityCategoryEntity(
        categoryNumber = "CATEGORY2"
    ) {
        override fun toDomain(): IdentityCategory = IdentityCategory.FIELD
    }

    data object ENVIRONMENT : IdentityCategoryEntity(
        categoryNumber = "CATEGORY3"
    ) {
        override fun toDomain(): IdentityCategory = IdentityCategory.ENVIRONMENT
    }

    data object INSPIRATION : IdentityCategoryEntity(
        categoryNumber = "CATEGORY4"
    ) {
        override fun toDomain(): IdentityCategory = IdentityCategory.INSPIRATION
    }
}

fun IdentityCategory.toData() : IdentityCategoryEntity = when (this) {
    IdentityCategory.EXPLAIN -> IdentityCategoryEntity.EXPLAIN
    IdentityCategory.FIELD -> IdentityCategoryEntity.FIELD
    IdentityCategory.ENVIRONMENT -> IdentityCategoryEntity.ENVIRONMENT
    IdentityCategory.INSPIRATION -> IdentityCategoryEntity.INSPIRATION
    IdentityCategory.NONE -> throw IllegalArgumentException("Invalid IdentityCategory: $this")
}
