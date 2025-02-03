package com.umc.edison.remote.model.mypage

import com.google.gson.annotations.SerializedName

data class UpdateProfileResponse(
    @SerializedName("nickname") val nickname: String,
    @SerializedName("imageUrl") val imageUrl: String,
)
