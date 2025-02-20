package com.umc.edison.presentation.artletter

import com.umc.edison.presentation.base.BaseState
import com.umc.edison.presentation.model.ArtLetterKeyWordModel
import com.umc.edison.presentation.model.ArtLetterPreviewModel

data class ArtLetterSearchState (
    override val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    override val error: Throwable? = null,
    override val toastMessage: String? = null,
    val artLetters: List<ArtLetterPreviewModel>,
    val recommendedArtLetters: List<ArtLetterPreviewModel> = emptyList(),
    val query: String = "",
    val lastQuery: String = "",
    val recentSearches: List<String> = emptyList(),
    val isSearchActivated: Boolean = false,
    val categories: List<String> = emptyList(),
    val keywords: List<ArtLetterKeyWordModel> = emptyList(),
    val showLoginModal: Boolean = false,
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