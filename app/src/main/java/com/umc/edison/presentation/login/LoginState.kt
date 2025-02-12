package com.umc.edison.presentation.login

import com.umc.edison.presentation.base.BaseState
import com.umc.edison.presentation.model.UserModel

data class LoginState(
    override val isLoading: Boolean,
    override val error: Throwable? = null,
    override val toastMessage: String? = null,
    val user: UserModel? = null,

    ):BaseState {
    companion object {
        val DEFAULT = LoginState(
            isLoading = false,
        )
    }
}