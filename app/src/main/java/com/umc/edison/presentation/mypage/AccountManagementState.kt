package com.umc.edison.presentation.mypage

import com.umc.edison.presentation.base.BaseState
import com.umc.edison.presentation.model.UserModel

data class AccountManagementState(
    override val isLoading: Boolean,
    val isLoggedIn: Boolean,
    val user: UserModel,
    override val error: Throwable?,
    override val toastMessage: String?
) : BaseState {
    companion object {
        val DEFAULT = AccountManagementState(
            isLoading = false,
            isLoggedIn = false,
            user = UserModel.DEFAULT,
            error = null,
            toastMessage = null
        )
    }
}
