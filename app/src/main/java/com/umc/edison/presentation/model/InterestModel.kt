package com.umc.edison.presentation.model

import com.umc.edison.domain.model.InterestKeyword

data class InterestModel(
    val category: InterestCategory,
    val keywords: List<KeywordModel>
)

fun InterestKeyword.toPresentation(): InterestModel {
    return InterestModel(
        category = InterestCategory.valueOf(question),
        keywords = keywords.map { it.toPresentation() }
    )
}

enum class InterestCategory(val question: String) {
    INSPIRATION("나의 상상력을 자극하는 분야는?"),
}
