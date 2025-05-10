package com.umc.edison.presentation.login

import com.umc.edison.presentation.model.UserModel

data class TermsOfUseState(
    val user: UserModel,
) {
    companion object {
        val DEFAULT = TermsOfUseState(
            user = UserModel.DEFAULT,
        )
    }
}
