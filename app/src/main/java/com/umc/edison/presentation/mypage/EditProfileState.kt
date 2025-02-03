package com.umc.edison.presentation.mypage

import com.umc.edison.presentation.base.BaseState
import com.umc.edison.presentation.model.UserModel

data class EditProfileState(
    override val isLoading: Boolean,
    val user: UserModel,
    override val error: Throwable? = null,
    override val toastMessage: String? = null,

) : BaseState {
    companion object {
        val DEFAULT = EditProfileState(
            isLoading = false,
            user = UserModel.DEFAULT,
            error = null,
            toastMessage = null
        )
    }
}
