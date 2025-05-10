package com.umc.edison.presentation.model

import com.umc.edison.domain.model.Interest
import com.umc.edison.domain.model.InterestCategory

data class InterestModel(
    val id: Int,
    val question: String,
    val questionTip: String,
    val descriptionFirst: String,
    val descriptionSecond: String?,
    val options: List<KeywordModel>,
    val selectedKeywords: List<KeywordModel>
) {
    fun toDomain(): Interest {
        return Interest(
            category = InterestCategory.entries[id],
            keywords = selectedKeywords.map { it.toDomain() },
            options = options.map { it.toDomain() }
        )
    }

    companion object {
        val DEFAULT = InterestModel(
            id = 0,
            question = InterestCategory.NONE.question,
            questionTip = InterestCategory.NONE.questionTip,
            descriptionFirst = InterestCategory.NONE.descriptionFirst,
            descriptionSecond = null,
            options = emptyList(),
            selectedKeywords = emptyList()
        )
    }
}

fun Interest.toPresentation(): InterestModel {
    return InterestModel(
        id = category.ordinal,
        question = category.question,
        questionTip = category.questionTip,
        descriptionFirst = category.descriptionFirst,
        descriptionSecond = category.descriptionSecond,
        options = options.map { it.toPresentation() },
        selectedKeywords = keywords.map { it.toPresentation() }
    )
}
