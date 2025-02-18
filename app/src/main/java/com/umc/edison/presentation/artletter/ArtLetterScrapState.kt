package com.umc.edison.presentation.artletter

import com.umc.edison.presentation.base.BaseState
import com.umc.edison.presentation.model.ArtLetterScrapModel

data class ArtLetterScrapState (
    override val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    override val error: Throwable? = null,
    override val toastMessage: String? = null,
    val result: ArtLetterScrapModel,
) : BaseState {
    companion object {
        val DEFAULT = ArtLetterScrapState(
            isLoading = false,
            isLoggedIn = false,
            error = null,
            toastMessage = null,
            result = ArtLetterScrapModel.DEFAULT,
        )
    }
}