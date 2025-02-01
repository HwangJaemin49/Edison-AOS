package com.umc.edison.presentation.model

import com.umc.edison.domain.model.Interest
import com.umc.edison.domain.model.Keyword

data class InterestModel(
    val id: Int,
    val question: String,
    val questionTip: String,
    val descriptionFirst: String,
    val descriptionSecond: String? = null,
    val options: List<Keyword>,
    val selectedKeywords: List<KeywordModel>
)

fun Interest.toPresentation(): InterestModel {
    return InterestModel(
        id = category.ordinal,
        question = category.question,
        questionTip = category.questionTip,
        descriptionFirst = category.descriptionFirst,
        descriptionSecond = category.descriptionSecond,
        options = options,
        selectedKeywords = keywords.map { it.toPresentation() }
    )
}
