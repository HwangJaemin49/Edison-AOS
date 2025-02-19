package com.umc.edison.presentation.artletter

import com.umc.edison.presentation.base.BaseState
import com.umc.edison.presentation.model.ArtLetterDetailModel
import com.umc.edison.presentation.model.ArtLetterPreviewModel

data class ArtLetterDetailState(
    override val isLoading: Boolean = false,
    override val error: Throwable? = null,
    override val toastMessage: String? = null,
    val artLetter: ArtLetterDetailModel,
    val relatedArtLetters: List<ArtLetterPreviewModel> = emptyList(),
    val isLoggedIn: Boolean = false,
    val showLoginModal: Boolean = false,
) : BaseState {
    companion object {
        val DEFAULT = ArtLetterDetailState(
            isLoading = false,
            isLoggedIn = false,
            error = null,
            toastMessage = null,
            artLetter = ArtLetterDetailModel.DEFAULT,
        )
    }
}