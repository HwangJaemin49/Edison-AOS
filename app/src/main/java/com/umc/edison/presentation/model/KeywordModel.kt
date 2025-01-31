package com.umc.edison.presentation.model

import com.umc.edison.domain.model.Keyword

data class KeywordModel(
    val id: Int,
    val name: String
)

fun Keyword.toPresentation(): KeywordModel {
    return KeywordModel(
        id = id,
        name = name
    )
}
