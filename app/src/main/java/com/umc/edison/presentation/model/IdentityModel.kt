package com.umc.edison.presentation.model

import com.umc.edison.domain.model.identity.Identity
import com.umc.edison.domain.model.identity.IdentityCategory

data class IdentityModel(
    val category: IdentityCategoryType,
    val question: String,
    val questionTip: String?,
    val descriptionFirst: String,
    val descriptionSecond: String?,
    val options: List<KeywordModel>,
    val selectedKeywords: List<KeywordModel>,
) {
    fun toDomain(): Identity {
        return Identity(
            category = category.toDomain(),
            selectedKeywords = selectedKeywords.map { it.toDomain() },
            keywords = options.map { it.toDomain() }
        )
    }

    companion object {
        val DEFAULT = IdentityModel(
            category = IdentityCategory.NONE.toType(),
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
        category = category.toType(),
        question = category.question,
        questionTip = category.questionTip,
        descriptionFirst = category.descriptionFirst,
        descriptionSecond = category.descriptionSecond,
        options = keywords.map { it.toPresentation() },
        selectedKeywords = selectedKeywords.map { it.toPresentation() }
    )
}

enum class IdentityCategoryType {
    NONE, EXPLAIN, FIELD, ENVIRONMENT, INSPIRATION
}

fun IdentityCategory.toType(): IdentityCategoryType = when (this) {
    IdentityCategory.NONE -> IdentityCategoryType.NONE
    IdentityCategory.EXPLAIN -> IdentityCategoryType.EXPLAIN
    IdentityCategory.FIELD -> IdentityCategoryType.FIELD
    IdentityCategory.ENVIRONMENT -> IdentityCategoryType.ENVIRONMENT
    IdentityCategory.INSPIRATION -> IdentityCategoryType.INSPIRATION
}

fun IdentityCategoryType.toDomain(): IdentityCategory = when (this) {
    IdentityCategoryType.NONE -> IdentityCategory.NONE
    IdentityCategoryType.EXPLAIN -> IdentityCategory.EXPLAIN
    IdentityCategoryType.FIELD -> IdentityCategory.FIELD
    IdentityCategoryType.ENVIRONMENT -> IdentityCategory.ENVIRONMENT
    IdentityCategoryType.INSPIRATION -> IdentityCategory.INSPIRATION
}

fun IdentityCategoryType.toIndex(): Int = when (this) {
    IdentityCategoryType.NONE -> 0
    IdentityCategoryType.EXPLAIN -> 1
    IdentityCategoryType.FIELD -> 2
    IdentityCategoryType.ENVIRONMENT -> 3
    IdentityCategoryType.INSPIRATION -> 4
}
