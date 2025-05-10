package com.umc.edison.presentation.mypage

import com.umc.edison.presentation.model.UserModel

data class AccountManagementState(
    val isLoggedIn: Boolean,
    val user: UserModel?,
    val mode: AccountManagementMode,
) {
    companion object {
        val DEFAULT = AccountManagementState(
            isLoggedIn = false,
            user = null,
            mode = AccountManagementMode.NONE,
        )
    }
}

enum class AccountManagementMode {
    NONE, LOGOUT
}
