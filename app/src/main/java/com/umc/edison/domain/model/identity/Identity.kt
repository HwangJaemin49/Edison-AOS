package com.umc.edison.domain.model.identity

data class Identity(
    val category: IdentityCategory,
    val selectedKeywords: List<IdentityKeyword>,
    val keywords: List<IdentityKeyword>
)

enum class IdentityCategory(
    val question: String,
    val questionTip: String? = null,
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
        questionTip = "여러가지를 골라도 괜찮아요!\n에디슨과 함께 언젠가 내가 이루게 될 꿈들을 체크해주세요!",
        descriptionFirst = "닉네임 님의 아이디어는",
        descriptionSecond = "이런 필드에서 빛을 발할 거예요!",
    ),

    ENVIRONMENT(
        question = "나에게 가장\n영감을 주는 환경은?",
        descriptionFirst = "닉네임 님은",
        descriptionSecond = "에서 가장 영감을 받아요!",
    ),

    INSPIRATION(
        question = "나의 상상력을\n자극하는 분야는?",
        questionTip = "잘 알지 못해도 관심있는 분야일수록 좋아요.",
        descriptionFirst = "닉네임 님은",
        descriptionSecond = "에서 가장 영감을 받아요!",
    ),
}
