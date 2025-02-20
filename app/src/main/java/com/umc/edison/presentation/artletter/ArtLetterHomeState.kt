package com.umc.edison.presentation.artletter

import com.umc.edison.presentation.base.BaseState
import com.umc.edison.presentation.model.ArtLetterPreviewModel

data class ArtLetterHomeState(
    override val isLoading: Boolean = false,
    override val error: Throwable? = null,
    override val toastMessage: String? = null,
    val isLoggedIn: Boolean = false,
    val artLetters: List<ArtLetterPreviewModel>,
    val editorsPick: List<ArtLetterPreviewModel>,
    val showLoginModal: Boolean = false,
) : BaseState {
    companion object {
        val DEFAULT = ArtLetterHomeState(
            isLoading = false,
            error = null,
            toastMessage = null,
            isLoggedIn = false,
            artLetters = emptyList(),
            editorsPick = emptyList(),
        )
    }
}