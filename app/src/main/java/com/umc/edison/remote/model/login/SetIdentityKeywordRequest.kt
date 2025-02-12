package com.umc.edison.remote.model.login

import com.google.gson.annotations.SerializedName
import com.umc.edison.data.model.IdentityEntity
import com.umc.edison.data.model.InterestEntity

data class SetIdentityKeywordRequest(
    @SerializedName("category")
    val category: String,
    @SerializedName("keywords")
    val keywords: List<Int>
)
fun IdentityEntity.toSetIdentityKeywordRequest(): SetIdentityKeywordRequest =SetIdentityKeywordRequest(
    category = categoryNumber,
    keywords = keywords.map { it.id }
)

fun InterestEntity.toSetIdentityKeywordRequest(): SetIdentityKeywordRequest =SetIdentityKeywordRequest(
    category = categoryNumber,
    keywords = keywords.map { it.id }
)
