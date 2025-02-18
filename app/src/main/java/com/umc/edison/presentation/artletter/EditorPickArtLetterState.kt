package com.umc.edison.presentation.artletter

import com.umc.edison.presentation.base.BaseState
import com.umc.edison.presentation.model.EditorPickArtLetterModel

data class EditorPickArtLetterState (
    val artletters: List<EditorPickArtLetterModel>,
    override val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    override val error: Throwable? = null,
    override val toastMessage: String? = null,
) : BaseState {
    companion object {
        val DEFAULT = EditorPickArtLetterState(
            artletters = emptyList(),
            isLoading = false,
            isLoggedIn = false,
            error = null,
            toastMessage = null
        )
    }
}