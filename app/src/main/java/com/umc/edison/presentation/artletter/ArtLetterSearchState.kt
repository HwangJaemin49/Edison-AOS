package com.umc.edison.presentation.artletter

import com.umc.edison.presentation.base.BaseState
import com.umc.edison.presentation.model.ArtLetterPreviewModel

data class ArtLetterSearchState (
    override val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    override val error: Throwable? = null,
    override val toastMessage: String? = null,
    val artLetters: List<ArtLetterPreviewModel>,
    val recommendedArtLetters: List<ArtLetterPreviewModel> = emptyList(),
) : BaseState {
    companion object {
        val DEFAULT = ArtLetterSearchState(
            isLoading = false,
            isLoggedIn = false,
            error = null,
            toastMessage = null,
            artLetters = emptyList(),
            recommendedArtLetters = emptyList(),
        )
    }
}