package com.umc.edison.data.model.user

import com.umc.edison.data.model.DataMapper
import com.umc.edison.domain.model.user.User

data class UserWithTokenEntity(
    val user: UserEntity,
    val accessToken: String,
    val refreshToken: String? = null
) : DataMapper<User> {
    override fun toDomain(): User {
        return user.toDomain()
    }
}
