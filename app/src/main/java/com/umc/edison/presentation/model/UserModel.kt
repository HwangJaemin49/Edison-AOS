package com.umc.edison.presentation.model

import com.umc.edison.domain.model.User

data class UserModel(
    val nickname: String,
    val profileImage: String?,
    val email: String,
) {
    companion object {
        val DEFAULT = UserModel(
            nickname = "닉네임",
            profileImage = null,
            email = "",
        )
    }
}

fun User.toPresentation(): UserModel {
    return UserModel(
        nickname = nickname,
        profileImage = profileImage,
        email = email,
    )
}
