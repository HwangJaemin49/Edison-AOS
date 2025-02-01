package com.umc.edison.domain.model

data class User(
    val nickname: String,
    val profileImage: String? = null,
    val email: String,
)
