package com.umc.edison.presentation.mypage

import com.umc.edison.domain.model.IdentityCategory
import com.umc.edison.presentation.model.KeywordModel

data class IdentityEditState(
    val isLoading: Boolean,
    val identityCategory: IdentityCategory,
    val selectedKeywords: List<KeywordModel> = listOf(),
    val options: List<KeywordModel> = listOf(),
    val error: Throwable? = null,
) {
    companion object {
        val DEFAULT = IdentityEditState(
            isLoading = false,
            identityCategory = IdentityCategory.EXPLAIN,
            selectedKeywords = listOf(),
            options = listOf()
        )
    }
}
