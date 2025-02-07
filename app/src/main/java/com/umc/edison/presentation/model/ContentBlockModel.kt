package com.umc.edison.presentation.model

import com.umc.edison.domain.model.ContentBlock
import com.umc.edison.domain.model.ContentType

data class ContentBlockModel(
    val type: ContentType,
    var content: String,
) {
    fun toDomain(): ContentBlock = ContentBlock(type, content)
}

fun ContentBlock.toPresentation(): ContentBlockModel =
    ContentBlockModel(type, content)

fun List<ContentBlock>.toPresentation(): List<ContentBlockModel> = map { it.toPresentation() }
