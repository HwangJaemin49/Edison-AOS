package com.umc.edison.presentation.mypage

import com.umc.edison.presentation.model.IdentityCategory
import com.umc.edison.presentation.model.IdentityModel
import com.umc.edison.presentation.model.InterestCategory
import com.umc.edison.presentation.model.InterestModel
import com.umc.edison.presentation.model.ScrapBoardModel

data class MyPageState(
    val isLoading: Boolean,
    val isLoggedIn: Boolean,
    val profileImage: String? = null,
    val nickname: String,
    val identity: List<IdentityModel> = listOf(),
    val interest: InterestModel,
    val artLetter: List<ScrapBoardModel>,
    val error: Throwable? = null,
) {
    companion object {
        val DEFAULT = MyPageState(
            isLoading = false,
            isLoggedIn = false,
            nickname = "닉네임",
            identity = listOf(
                IdentityModel(
                    category = IdentityCategory.EXPLAIN,
                    selectedKeywords = listOf()
                ),
                IdentityModel(
                    category = IdentityCategory.FIELD,
                    selectedKeywords = listOf()
                ),
                IdentityModel(
                    category = IdentityCategory.ENVIRONMENT,
                    selectedKeywords = listOf()
                )
            ),
            interest = InterestModel(
                category = InterestCategory.INSPIRATION,
                keywords = listOf()
            ),
            artLetter = listOf()
        )
    }
}
