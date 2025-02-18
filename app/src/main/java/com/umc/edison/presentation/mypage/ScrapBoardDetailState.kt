package com.umc.edison.presentation.mypage

import com.umc.edison.presentation.base.BaseState
import com.umc.edison.presentation.model.ArtLetterModel

data class ScrapBoardDetailState(
    override val isLoading: Boolean,
    override val error: Throwable?,
    override val toastMessage: String?,
    val categoryName: String = "",
    val artLetters: List<ArtLetterModel>
) : BaseState {
    companion object {
        val DEFAULT = ScrapBoardDetailState(
            isLoading = false,
            error = null,
            toastMessage = null,
            artLetters = emptyList()
        )
    }
}
