package com.umc.edison.remote.model.mypage

import com.google.gson.annotations.SerializedName

data class GetIdentityKeywordResponse(
    @SerializedName("categories") val categories: String, // TODO 명세서 수정 이후에 반영
)
