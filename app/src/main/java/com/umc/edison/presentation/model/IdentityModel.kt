package com.umc.edison.presentation.model

data class IdentityModel(
    val category: IdentityCategory,
    val keywords: List<KeywordModel>
)

enum class IdentityCategory(name: String) {
    EXPLAIN("나를 설명하는 단어를 골라주세요!"),
    FIELD("지금 혹은 미래의 나는 어떤 필드에 있나요?"),
    ENVIRONMENT("나에게 가장 영감을 주는 환경은?"),
}
