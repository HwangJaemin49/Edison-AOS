package com.umc.edison.remote.model.mypage

import com.google.gson.annotations.SerializedName
import com.umc.edison.data.model.IdentityEntity

data class UpdateIdentityRequest(
    @SerializedName("category") val category: String,
    @SerializedName("keywords") val keywords: List<Int>,
)

fun IdentityEntity.toUpdateIdentityRequest(): UpdateIdentityRequest = UpdateIdentityRequest(
    category = categoryNumber,
    keywords = keywords.map { it.id }
)
