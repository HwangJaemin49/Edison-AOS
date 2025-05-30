package com.umc.edison.presentation.model

import com.umc.edison.domain.model.user.User

data class UserModel(
    val nickname: String?,
    val profileImage: String?,
    val email: String,
    val isNewMember: Boolean
) {
    companion object {
        val DEFAULT = UserModel(
            nickname = null,
            profileImage = null,
            email = "",
            isNewMember = false
        )
    }

    fun toDomain(): User {
        return User(
            nickname = nickname,
            profileImage = profileImage,
            email = email,
            isNewMember = isNewMember
        )
    }
}

fun User.toPresentation(): UserModel {
    return UserModel(
        nickname = nickname,
        profileImage = profileImage,
        email = email,
        isNewMember = isNewMember
    )
}
