package com.umc.edison.presentation.login

import com.umc.edison.presentation.base.BaseState

data class IdentityTestState(
    override val isLoading: Boolean,
    override val error: Throwable? = null,
    override val toastMessage: String? = null

): BaseState {
    companion object {
        val DEFAULT = IdentityTestState(
            isLoading = false,
        )
    }
}