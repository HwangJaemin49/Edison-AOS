package com.umc.edison.remote.model.mypage

import com.google.gson.annotations.SerializedName
import com.umc.edison.data.model.UserEntity

data class UpdateProfileRequest(
    @SerializedName("nickname") val nickname: String?,
    @SerializedName("imageUrl") val imageUrl: String?,
)

fun UserEntity.toUpdateProfileRequest(): UpdateProfileRequest = UpdateProfileRequest(
    nickname = nickname,
    imageUrl = profileImage,
)
