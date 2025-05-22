package com.umc.edison.remote.model.mypage

import com.google.gson.annotations.SerializedName
import com.umc.edison.data.model.identity.IdentityEntity

data class UpdateTestRequest(
    @SerializedName("category") val category: String,
    @SerializedName("keywords") val keywords: List<Int>,
)

fun IdentityEntity.toUpdateTestRequest(): UpdateTestRequest = UpdateTestRequest(
    category = categoryNumber,
    keywords = selectedKeywords.map { it.id }
)
