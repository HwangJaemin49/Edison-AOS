package com.umc.edison.remote.model.mypage

import com.google.gson.annotations.SerializedName

data class UpdateIdentityResponse(
    @SerializedName("category") val category: String,
    @SerializedName("keywords") val keywords: List<Int>,
)
