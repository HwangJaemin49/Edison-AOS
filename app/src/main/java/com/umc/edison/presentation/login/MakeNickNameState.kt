package com.umc.edison.presentation.login

import com.umc.edison.presentation.model.UserModel

data class MakeNickNameState(
    val user: UserModel,
) {
    companion object {
        val DEFAULT = MakeNickNameState(
            user = UserModel.DEFAULT,
        )
    }
}
