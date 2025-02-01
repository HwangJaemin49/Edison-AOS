package com.umc.edison.domain.model

data class IdentityKeyword(
    val question: String,
    val selectedKeywords: List<Keyword> = emptyList(),
)
