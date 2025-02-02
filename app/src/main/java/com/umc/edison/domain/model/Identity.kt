package com.umc.edison.domain.model

data class Identity(
    val category: IdentityCategory,
    val selectedKeywords: List<Keyword> = emptyList(),
    val options: List<Keyword>
)

enum class IdentityCategory(
    val question: String,
    val descriptionFirst: String,
    val descriptionSecond: String? = null,
) {
    NONE(
        question = "",
        descriptionFirst = "",
    ),
    EXPLAIN(
        question = "나를 설명하는\n단어를 골라주세요!",
        descriptionFirst = "닉네임 님은 자신을 이렇게 바라보고 있어요.",
    ),

    FIELD(
        question = "지금 혹은 미래의 나는\n어떤 필드에 있나요?",
        descriptionFirst = "닉네임 님의 아이디어는",
        descriptionSecond = "이런 필드에서 빛을 발할 거예요!",
    ),

    ENVIRONMENT(
        question = "나에게 가장\n영감을 주는 환경은?",
        descriptionFirst = "닉네임 님은",
        descriptionSecond = "에서 가장 영감을 받아요!",
    )
}
