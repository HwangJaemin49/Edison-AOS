package com.umc.edison.presentation.mypage

import com.umc.edison.presentation.base.BaseState

data class DeleteAccountState(
    override val isLoading: Boolean,
    override val error: Throwable?,
    override val toastMessage: String?,
    val isAgree: Boolean,
    val isDeleted: Boolean,
) : BaseState {
    companion object {
        val DEFAULT = DeleteAccountState(
            isLoading = false,
            error = null,
            toastMessage = null,
            isAgree = false,
            isDeleted = false
        )
    }
}
