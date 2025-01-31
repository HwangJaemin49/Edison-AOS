package com.umc.edison.presentation.model

data class InterestModel(
    val category: InterestCategory,
    val keywords: List<KeywordModel>
)

enum class InterestCategory(name: String) {
    INSPIRATION("나의 상상력을 자극하는 분야는?"),
}
