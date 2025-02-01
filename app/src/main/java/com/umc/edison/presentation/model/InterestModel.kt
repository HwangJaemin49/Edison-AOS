package com.umc.edison.presentation.model

import com.umc.edison.domain.model.InterestKeyword

data class InterestModel(
    val category: InterestCategory,
    val selectedKeywords: List<KeywordModel>
)

fun InterestKeyword.toPresentation(): InterestModel {
    return InterestModel(
        category = InterestCategory.valueOf(question),
        selectedKeywords = keywords.map { it.toPresentation() }
    )
}

enum class InterestCategory(
    val question: String,
    val questionTip: String,
    val descriptionFirst: String,
    val descriptionSecond: String? = null,
    val options: List<KeywordModel>
) {
    INSPIRATION(
        question = "나의 상상력을\n자극하는 분야는?",
        questionTip = "잘 알지 못해도 관심있는 분야일수록 좋아요.",
        descriptionFirst = "닉네임 님은",
        descriptionSecond = "에서 가장 영감을 받아요!",
        options = listOf(
            KeywordModel(1, "문학"),
            KeywordModel(2, "자연"),
            KeywordModel(3, "과학"),
            KeywordModel(4, "천문학"),
            KeywordModel(5, "물리학"),
            KeywordModel(6, "철학"),
            KeywordModel(7, "역사"),
            KeywordModel(8, "IT 혁신"),
            KeywordModel(9, "ESG"),
            KeywordModel(10, "회화"),
            KeywordModel(11, "비즈니스"),
            KeywordModel(12, "현대미술"),
            KeywordModel(13, "조각"),
            KeywordModel(14, "공예"),
            KeywordModel(15, "음악"),
            KeywordModel(16, "패션"),
            KeywordModel(17, "연극"),
            KeywordModel(18, "춤"),
            KeywordModel(19, "여행"),
            KeywordModel(20, "스포츠"),
            KeywordModel(21, "심리학"),
            KeywordModel(22, "사회문화"),
            KeywordModel(23, "게임"),
            KeywordModel(24, "영화"),
            KeywordModel(25, "사진"),
            KeywordModel(26, "건축"),
            KeywordModel(27, "만화"),
            KeywordModel(28, "디자인"),
            KeywordModel(29, "애니메이션"),
            KeywordModel(30, "전통")
        )
    ),
}
