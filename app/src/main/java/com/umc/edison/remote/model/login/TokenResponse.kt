package com.umc.edison.remote.model.login

import com.google.gson.annotations.SerializedName

data class TokenResponse(
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("refreshToken")
    val refreshToken: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("isNewMember")
    val isNewMember:Boolean
)