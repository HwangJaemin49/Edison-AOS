package com.umc.edison.presentation.artletter

import com.umc.edison.presentation.base.BaseState
import com.umc.edison.presentation.model.ArtLetterDetailModel
import com.umc.edison.presentation.model.ArtLetterMarkModel

data class ArtLetterMarkState (
    override val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    override val error: Throwable? = null,
    override val toastMessage: String? = null,
    val result: ArtLetterMarkModel,
) : BaseState {
    companion object {
        val DEFAULT = ArtLetterMarkState(
            isLoading = false,
            isLoggedIn = false,
            error = null,
            toastMessage = null,
            result = ArtLetterMarkModel.DEFAULT,
        )
    }
}