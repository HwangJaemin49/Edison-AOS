package com.umc.edison.presentation.artletter

import com.umc.edison.presentation.model.ArtLetterKeyWordModel
import com.umc.edison.presentation.model.ArtLetterPreviewModel

data class ArtLetterSearchState(
    val isLoggedIn: Boolean,
    val artLetters: List<ArtLetterPreviewModel>,
    val recommendedArtLetters: List<ArtLetterPreviewModel>,
    val query: String,
    val lastQuery: String,
    val recentSearches: List<String>,
    val isSearchActivated: Boolean,
    val categories: List<String>,
    val keywords: List<ArtLetterKeyWordModel>,
    val showLoginModal: Boolean,
) {
    companion object {
        val DEFAULT = ArtLetterSearchState(
            isLoggedIn = false,
            artLetters = emptyList(),
            recommendedArtLetters = emptyList(),
            query = "",
            lastQuery = "",
            recentSearches = emptyList(),
            isSearchActivated = false,
            categories = emptyList(),
            keywords = emptyList(),
            showLoginModal = false,
        )
    }
}
