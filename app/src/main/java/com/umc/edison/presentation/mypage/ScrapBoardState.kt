package com.umc.edison.presentation.mypage

import com.umc.edison.presentation.base.BaseState
import com.umc.edison.presentation.model.ArtLetterCategoryModel

data class ScrapBoardState(
    override val isLoading: Boolean,
    override val error: Throwable?,
    override val toastMessage: String?,
    val myArtLetterCategories: List<ArtLetterCategoryModel>,
) : BaseState {
    companion object {
        val DEFAULT = ScrapBoardState(
            isLoading = false,
            error = null,
            toastMessage = null,
            myArtLetterCategories = listOf()
        )
    }
}
