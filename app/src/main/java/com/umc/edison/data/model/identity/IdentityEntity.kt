package com.umc.edison.data.model.identity

import com.umc.edison.data.model.DataMapper
import com.umc.edison.domain.model.identity.Identity
import com.umc.edison.domain.model.identity.IdentityCategory

data class IdentityEntity(
    val category: IdentityCategory,
    val selectedKeywords: List<IdentityKeywordEntity>,
    val keywords: List<IdentityKeywordEntity>,
) : DataMapper<Identity> {
    override fun toDomain(): Identity {
        return Identity(
            category = category,
            selectedKeywords = selectedKeywords.map { it.toDomain() },
            keywords = keywords.map { it.toDomain() }
        )
    }
}

fun Identity.toData(): IdentityEntity {
    return IdentityEntity(
        category = category,
        selectedKeywords = selectedKeywords.map { it.toData() },
        keywords = keywords.map { it.toData() }
    )
}
