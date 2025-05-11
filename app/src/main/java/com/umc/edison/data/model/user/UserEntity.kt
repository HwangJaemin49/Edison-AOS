package com.umc.edison.data.model

data class UserEntity(
    val nickname: String?,
    val profileImage: String?,
    val email: String,
    val isNewMember: Boolean
) : DataMapper<User> {
    override fun toDomain(): User {
        return User(
            nickname = nickname,
            profileImage = profileImage,
            email = email,
            isNewMember = isNewMember
        )
    }
}

fun User.toData(): UserEntity {
    return UserEntity(
        nickname = nickname,
        profileImage = profileImage,
        email = email,
        isNewMember = isNewMember
    )
}