package com.umc.edison.presentation.mypage

import com.umc.edison.presentation.base.BaseState
import com.umc.edison.presentation.model.UserModel

data class AccountManagementState(
    override val isLoading: Boolean,
    val isLoggedIn: Boolean,
    val user: UserModel? = null,
    val mode: AccountManagementMode = AccountManagementMode.NONE,
    override val error: Throwable?,
    override val toastMessage: String?
) : BaseState {
    companion object {
        val DEFAULT = AccountManagementState(
            isLoading = false,
            isLoggedIn = false,
            error = null,
            toastMessage = null
        )
    }
}

enum class AccountManagementMode {
    NONE, LOGOUT, DELETE_ACCOUNT
}
