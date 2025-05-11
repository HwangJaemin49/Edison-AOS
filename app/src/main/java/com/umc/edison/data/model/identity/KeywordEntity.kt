package com.umc.edison.data.model

import com.umc.edison.domain.model.identity.IdentityKeyword

data class KeywordEntity(
    val id: Int,
    val name: String
) : DataMapper<IdentityKeyword> {
    override fun toDomain(): IdentityKeyword {
        return IdentityKeyword(
            id = id,
            name = name
        )
    }
}

fun IdentityKeyword.toData(): KeywordEntity {
    return KeywordEntity(
        id = id,
        name = name
    )
}
