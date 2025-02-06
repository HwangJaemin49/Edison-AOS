package com.umc.edison.presentation.artletter


import com.umc.edison.presentation.model.ArtLetterModel

data class ArtLetterState(
    val artletters: ArtLetterModel,
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    val error: Throwable? = null,
    val toastMessage: String? = null,
)