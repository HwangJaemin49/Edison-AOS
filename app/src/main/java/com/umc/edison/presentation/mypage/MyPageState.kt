package com.umc.edison.presentation.mypage

import com.umc.edison.domain.model.identity.IdentityCategory
import com.umc.edison.presentation.model.IdentityModel
import com.umc.edison.presentation.model.ArtLetterCategoryModel
import com.umc.edison.presentation.model.UserModel
import com.umc.edison.presentation.model.toType

data class MyPageState(
    val isLoggedIn: Boolean,
    val user: UserModel,
    val identities: List<IdentityModel>,
    val interest: IdentityModel,
    val myArtLetterCategories: List<ArtLetterCategoryModel>,
) {
    companion object {
        val DEFAULT = MyPageState(
            isLoggedIn = false,
            user = UserModel.DEFAULT,
            identities = listOf(
                IdentityModel(
                    category = IdentityCategory.EXPLAIN.toType(),
                    question = IdentityCategory.EXPLAIN.question,
                    questionTip = null,
                    descriptionFirst = IdentityCategory.EXPLAIN.descriptionFirst,
                    descriptionSecond = IdentityCategory.EXPLAIN.descriptionSecond,
                    options = emptyList(),
                    selectedKeywords = emptyList(),
                ),
                IdentityModel(
                    category = IdentityCategory.FIELD.toType(),
                    question = IdentityCategory.FIELD.question,
                    questionTip = null,
                    descriptionFirst = IdentityCategory.FIELD.descriptionFirst,
                    descriptionSecond = IdentityCategory.FIELD.descriptionSecond,
                    options = listOf(),
                    selectedKeywords = listOf()
                ),
                IdentityModel(
                    category = IdentityCategory.ENVIRONMENT.toType(),
                    question = IdentityCategory.ENVIRONMENT.question,
                    questionTip = null,
                    descriptionFirst = IdentityCategory.ENVIRONMENT.descriptionFirst,
                    descriptionSecond = IdentityCategory.ENVIRONMENT.descriptionSecond,
                    options = listOf(),
                    selectedKeywords = listOf()
                )
            ),
            interest = IdentityModel(
                category = IdentityCategory.INSPIRATION.toType(),
                question = IdentityCategory.INSPIRATION.question,
                questionTip = IdentityCategory.INSPIRATION.questionTip,
                descriptionFirst = IdentityCategory.INSPIRATION.descriptionFirst,
                descriptionSecond = IdentityCategory.INSPIRATION.descriptionSecond,
                options = listOf(),
                selectedKeywords = listOf()
            ),
            myArtLetterCategories = listOf()
        )
    }
}
