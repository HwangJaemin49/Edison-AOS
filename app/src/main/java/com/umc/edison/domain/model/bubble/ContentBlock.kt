package com.umc.edison.domain.model.bubble

data class ContentBlock(
    val type: ContentType,
    val content: String,
    val position: Int,
)