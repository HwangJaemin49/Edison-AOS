package com.umc.edison.remote.model.login

import com.google.gson.annotations.SerializedName
import com.umc.edison.data.model.user.UserEntity
import com.umc.edison.data.model.user.UserWithTokenEntity
import com.umc.edison.remote.model.RemoteMapper

data class TokenResponse(
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("refreshToken")
    val refreshToken: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("isNewMember")
    val isNewMember:Boolean
): RemoteMapper<UserWithTokenEntity> {
    override fun toData(): UserWithTokenEntity =
        UserWithTokenEntity(
            user = toUserEntity(),
            accessToken = accessToken,
            refreshToken = refreshToken
        )

    fun toUserEntity(): UserEntity =
        UserEntity(
            nickname = null,
            profileImage = null,
            email = email,
            isNewMember = isNewMember,
        )
}