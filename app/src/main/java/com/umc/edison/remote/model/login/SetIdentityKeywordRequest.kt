package com.umc.edison.remote.model.login

import com.google.gson.annotations.SerializedName
import com.umc.edison.data.model.identity.IdentityEntity

data class SetIdentityKeywordRequest(
    @SerializedName("category")
    val category: String,
    @SerializedName("keywords")
    val keywords: List<Int>
)

fun IdentityEntity.toSetIdentityKeywordRequest(): SetIdentityKeywordRequest =
    SetIdentityKeywordRequest(
        category = category.categoryNumber,
        keywords = selectedKeywords.map { it.id }
    )
