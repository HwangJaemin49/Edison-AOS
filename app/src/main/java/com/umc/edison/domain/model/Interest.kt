package com.umc.edison.domain.model

data class Interest(
    val category: InterestCategory,
    val keywords: List<Keyword>,
    val options: List<Keyword>,
)

enum class InterestCategory(
    val question: String,
    val questionTip: String,
    val descriptionFirst: String,
    val descriptionSecond: String? = null,
) {
    INSPIRATION(
        question = "나의 상상력을\n자극하는 분야는?",
        questionTip = "잘 알지 못해도 관심있는 분야일수록 좋아요.",
        descriptionFirst = "닉네임 님은",
        descriptionSecond = "에서 가장 영감을 받아요!",
    ),
}
