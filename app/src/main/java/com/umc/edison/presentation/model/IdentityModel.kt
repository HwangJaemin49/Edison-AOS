package com.umc.edison.presentation.model

import com.umc.edison.domain.model.Identity
import com.umc.edison.domain.model.IdentityCategory

data class IdentityModel(
    val id: Int,
    val question: String,
    val questionTip: String?,
    val descriptionFirst: String,
    val descriptionSecond: String?,
    val options: List<KeywordModel>,
    val selectedKeywords: List<KeywordModel>,
) {
    fun toDomain(): Identity {
        return Identity(
            category = IdentityCategory.entries[id],
            selectedKeywords = selectedKeywords.map { it.toDomain() },
            options = options.map { it.toDomain() }
        )
    }

    companion object {
        val DEFAULT = IdentityModel(
            id = IdentityCategory.NONE.ordinal,
            question = IdentityCategory.NONE.question,
            questionTip = null,
            descriptionFirst = IdentityCategory.NONE.descriptionFirst,
            descriptionSecond = null,
            options = emptyList(),
            selectedKeywords = emptyList()
        )
    }
}

fun Identity.toPresentation(): IdentityModel {
    return IdentityModel(
        id = category.ordinal,
        question = category.question,
        questionTip = category.questionTip,
        descriptionFirst = category.descriptionFirst,
        descriptionSecond = category.descriptionSecond,
        options = options.map { it.toPresentation() },
        selectedKeywords = selectedKeywords.map { it.toPresentation() }
    )
}
