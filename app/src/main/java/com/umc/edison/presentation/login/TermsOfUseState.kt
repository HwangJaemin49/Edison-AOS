package com.umc.edison.presentation.login

import com.umc.edison.presentation.base.BaseState
import com.umc.edison.presentation.model.UserModel


data class TermsOfUseState (
    override val isLoading: Boolean,
    val user: UserModel,
    override val error: Throwable? = null,
    override val toastMessage: String? = null,

    ) : BaseState {

    companion object {
        val DEFAULT = TermsOfUseState(
            isLoading = false,
            user = UserModel.DEFAULT,
            error = null,
            toastMessage = null
        )
    }
}