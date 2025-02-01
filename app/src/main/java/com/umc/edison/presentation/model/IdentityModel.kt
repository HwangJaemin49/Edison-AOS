package com.umc.edison.presentation.model

import com.umc.edison.domain.model.IdentityKeyword

data class IdentityModel(
    val category: IdentityCategory,
    val selectedKeywords: List<KeywordModel>,
)

fun IdentityKeyword.toPresentation(): IdentityModel {
    return IdentityModel(
        category = IdentityCategory.valueOf(question),
        selectedKeywords = selectedKeywords.map { it.toPresentation() },
    )
}

enum class IdentityCategory(
    val question: String,
    val descriptionFirst: String,
    val descriptionSecond: String? = null,
    val options: List<KeywordModel>
) {
    EXPLAIN(
        question = "나를 설명하는\n단어를 골라주세요!",
        descriptionFirst = "닉네임 남은 자신을 이렇게 바라보고 있어요.",
        options = listOf(
            KeywordModel(1, "창의적인"),
            KeywordModel(2, "침착한"),
            KeywordModel(3, "사려 깊은"),
            KeywordModel(4, "정직한"),
            KeywordModel(5, "결단력 있는"),
            KeywordModel(6, "포용적인"),
            KeywordModel(7, "끈기 있는"),
            KeywordModel(8, "용감한"),
            KeywordModel(9, "공감하는"),
            KeywordModel(10, "정확한")
        )
    ),

    FIELD(
        question = "지금 혹은 미래의 나는\n어떤 필드에 있나요?",
        descriptionFirst = "닉네임 님의 아이디어는",
        descriptionSecond = "이런 필드에서 빛을 발할 거예요!",
        options = listOf(
            KeywordModel(11, "문학"),
            KeywordModel(12, "IT 혁신"),
            KeywordModel(13, "비즈니스"),
            KeywordModel(14, "마케팅"),
            KeywordModel(15, "디자인"),
            KeywordModel(16, "데이터 분석"),
            KeywordModel(17, "서비스 기획"),
            KeywordModel(18, "교육"),
            KeywordModel(19, "글로벌"),
            KeywordModel(20, "게임")
        )
    ),

    ENVIRONMENT(
        question = "나에게 가장\n영감을 주는 환경은?",
        descriptionFirst = "닉네임 님은",
        descriptionSecond = "에서 가장 영감을 받아요!",
        options = listOf(
            KeywordModel(21, "\uD83D\uDC69\u200D\uD83D\uDCBB 조용한 공간에서 혼자 있을 때"),
            KeywordModel(22, "☕ 사람들이 많은 카페나 공공장소에서"),
            KeywordModel(23, "\uD83C\uDF3F 자연 속에서 산책하거나 쉴 때"),
            KeywordModel(24, "\uD83D\uDCDA 다양한 책이나 자료를 읽을 때"),
            KeywordModel(25, "\uD83D\uDDBC\uFE0F 음악이나 예술 작품을 감상할 때"),
            KeywordModel(26, "\uD83D\uDCAC 관련된 사람들과 대화하거나 토론할 때"),
            KeywordModel(27, "\uD83E\uDD7D 실험하거나 직접 만들어볼 때"),
            KeywordModel(28, "\uD83C\uDF92 여행을 하거나 새로운 장소를 방문할 때"),
            KeywordModel(29, "\uD83D\uDCAD 과거 경험을 떠올릴 때"),
            KeywordModel(30, "\uD83E\uDDD8 아무것도 하지 않고 명상할 때")
        )
    )
}
