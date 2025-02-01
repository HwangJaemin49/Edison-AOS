package com.umc.edison.presentation.mypage

import com.umc.edison.domain.model.IdentityCategory
import com.umc.edison.domain.model.InterestCategory
import com.umc.edison.presentation.base.BaseState
import com.umc.edison.presentation.model.IdentityModel
import com.umc.edison.presentation.model.InterestModel
import com.umc.edison.presentation.model.ArtLetterCategoryModel
import com.umc.edison.presentation.model.UserModel

data class MyPageState(
    override val isLoading: Boolean,
    val isLoggedIn: Boolean,
    val user: UserModel,
    val identities: List<IdentityModel> = listOf(),
    val interest: InterestModel,
    val myArtLetterCategories: List<ArtLetterCategoryModel>,
    override val error: Throwable? = null,
    override val errorMessage: String? = null,
) : BaseState {
    companion object {
        val DEFAULT = MyPageState(
            isLoading = false,
            isLoggedIn = false,
            user = UserModel.DEFAULT,
            identities = listOf(
                IdentityModel(
                    id = IdentityCategory.EXPLAIN.ordinal,
                    question = IdentityCategory.EXPLAIN.question,
                    descriptionFirst = IdentityCategory.EXPLAIN.descriptionFirst,
                    descriptionSecond = IdentityCategory.EXPLAIN.descriptionSecond,
                    options = listOf(),
                    selectedKeywords = listOf()
                ),
                IdentityModel(
                    id = IdentityCategory.FIELD.ordinal,
                    question = IdentityCategory.FIELD.question,
                    descriptionFirst = IdentityCategory.FIELD.descriptionFirst,
                    descriptionSecond = IdentityCategory.FIELD.descriptionSecond,
                    options = listOf(),
                    selectedKeywords = listOf()
                ),
                IdentityModel(
                    id = IdentityCategory.ENVIRONMENT.ordinal,
                    question = IdentityCategory.ENVIRONMENT.question,
                    descriptionFirst = IdentityCategory.ENVIRONMENT.descriptionFirst,
                    descriptionSecond = IdentityCategory.ENVIRONMENT.descriptionSecond,
                    options = listOf(),
                    selectedKeywords = listOf()
                )
            ),
            interest = InterestModel(
                id = InterestCategory.INSPIRATION.ordinal,
                question = InterestCategory.INSPIRATION.question,
                questionTip = InterestCategory.INSPIRATION.questionTip,
                descriptionFirst = InterestCategory.INSPIRATION.descriptionFirst,
                descriptionSecond = InterestCategory.INSPIRATION.descriptionSecond,
                options = listOf(),
                selectedKeywords = listOf()
            ),
            myArtLetterCategories = listOf()
        )
    }
}
