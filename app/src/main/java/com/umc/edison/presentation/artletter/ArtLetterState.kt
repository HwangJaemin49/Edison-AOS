package com.umc.edison.presentation.artletter


import com.umc.edison.presentation.base.BaseState
import com.umc.edison.presentation.model.ArtLetterModel

data class ArtLetterState(
    val artletters: ArtLetterModel,
    override val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    override val error: Throwable? = null,
    override val toastMessage: String? = null,
) : BaseState {
    companion object {
        val DEFAULT = ArtLetterState(
            artletters = ArtLetterModel.DEFAULT,
            isLoading = false,
            error = null,
            toastMessage = null
        )
    }
}