package com.umc.edison.remote.model.mypage

import com.google.gson.annotations.SerializedName
import com.umc.edison.data.model.identity.IdentityKeywordEntity

data class GetIdentityKeywordResponse(
    @SerializedName("keywordId") val id: Int,
    @SerializedName("keywordName") val keyword: String,
) {
    fun toData() = IdentityKeywordEntity(id, keyword)
}
