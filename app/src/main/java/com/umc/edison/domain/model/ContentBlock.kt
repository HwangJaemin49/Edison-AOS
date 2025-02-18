package com.umc.edison.domain.model

data class ContentBlock(
    val type: ContentType,
    var content: String,
    var position: Int,
)
