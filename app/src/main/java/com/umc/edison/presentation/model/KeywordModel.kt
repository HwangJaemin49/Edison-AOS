package com.umc.edison.presentation.model

import com.umc.edison.domain.model.identity.IdentityKeyword

data class KeywordModel(
    val id: Int,
    val name: String
) {
    fun toDomain(): IdentityKeyword {
        return IdentityKeyword(
            id = id,
            name = name
        )
    }
}

fun IdentityKeyword.toPresentation(): KeywordModel {
    return KeywordModel(
        id = id,
        name = name
    )
}
