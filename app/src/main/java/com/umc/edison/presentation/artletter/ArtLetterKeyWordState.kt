package com.umc.edison.presentation.artletter

import com.umc.edison.presentation.base.BaseState
import com.umc.edison.presentation.model.ArtLetterKeyWordModel


data class ArtLetterKeyWordState (
    override val isLoading: Boolean = false,
    override val error: Throwable? = null,
    override val toastMessage: String? = null,
    val keywords: List<ArtLetterKeyWordModel>,
) : BaseState {
    companion object {
        val DEFAULT = ArtLetterKeyWordState(
            isLoading = false,
            error = null,
            toastMessage = null,
            keywords = emptyList()
        )
    }
}