package com.umc.edison.presentation.artletter

import com.umc.edison.presentation.base.BaseState
import com.umc.edison.presentation.model.ArtLetterDetailModel

data class ArtLetterDetailState(
    val artletter: ArtLetterDetailModel,
    override val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    override val error: Throwable? = null,
    override val toastMessage: String? = null,
) : BaseState {
    companion object {
        val DEFAULT = ArtLetterDetailState(
            artletter = ArtLetterDetailModel.DEFAULT,
            isLoading = false,
            isLoggedIn = false,
            error = null,
            toastMessage = null
        )
    }
}