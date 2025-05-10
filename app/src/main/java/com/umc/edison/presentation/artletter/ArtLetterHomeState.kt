package com.umc.edison.presentation.artletter

import com.umc.edison.presentation.model.ArtLetterPreviewModel

data class ArtLetterHomeState(
    val isLoggedIn: Boolean,
    val artLetters: List<ArtLetterPreviewModel>,
    val editorsPick: List<ArtLetterPreviewModel>,
    val showLoginModal: Boolean,
) {
    companion object {
        val DEFAULT = ArtLetterHomeState(
            isLoggedIn = false,
            artLetters = emptyList(),
            editorsPick = emptyList(),
            showLoginModal = false,
        )
    }
}
