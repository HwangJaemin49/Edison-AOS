package com.umc.edison.presentation.artletter


import com.umc.edison.presentation.base.BaseState
import com.umc.edison.presentation.model.ArtLetterModel

data class ArtLetterState(
    val artletters: List<ArtLetterModel>,
    override val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    override val error: Throwable? = null,
    override val toastMessage: String? = null,
) : BaseState {
    companion object {
        val DEFAULT = ArtLetterState(
            artletters = emptyList(),
            isLoading = false,
            isLoggedIn = false,
            error = null,
            toastMessage = null
        )
    }
}