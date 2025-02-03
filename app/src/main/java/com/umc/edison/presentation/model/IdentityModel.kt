package com.umc.edison.presentation.model

import com.umc.edison.domain.model.Identity
import com.umc.edison.domain.model.IdentityCategory

data class IdentityModel(
    val id: Int,
    val question: String,
    val descriptionFirst: String,
    val descriptionSecond: String? = null,
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
}

fun Identity.toPresentation(): IdentityModel {
    return IdentityModel(
        id = category.ordinal,
        question = category.question,
        descriptionFirst = category.descriptionFirst,
        descriptionSecond = category.descriptionSecond,
        options = options.map { it.toPresentation() },
        selectedKeywords = selectedKeywords.map { it.toPresentation() }
    )
}
