package com.umc.edison.remote.model.login

import com.google.gson.annotations.SerializedName

data class NicknameRequest(
    @SerializedName("nickname")
    val nickname: String
)