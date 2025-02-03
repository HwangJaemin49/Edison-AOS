package com.umc.edison.data.model

import com.umc.edison.domain.model.User

data class UserEntity(
    val nickname: String,
    val profileImage: String?,
    val email: String,
) : DataMapper<User> {
    override fun toDomain(): User {
        return User(
            nickname = nickname,
            profileImage = profileImage,
            email = email
        )
    }
}

fun User.toData(): UserEntity {
    return UserEntity(
        nickname = nickname,
        profileImage = profileImage,
        email = email
    )
}