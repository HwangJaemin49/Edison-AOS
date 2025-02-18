package com.umc.edison.presentation.model

import com.umc.edison.domain.model.ContentBlock
import com.umc.edison.domain.model.ContentType

data class ContentBlockModel(
    val type: ContentType,
    var content: String,
    var position: Int,
) {
    fun toDomain(): ContentBlock = ContentBlock(type, content, position)
}

fun ContentBlock.toPresentation(): ContentBlockModel =
    ContentBlockModel(type, content, position)

fun List<ContentBlock>.toPresentation(): List<ContentBlockModel> = map { it.toPresentation() }
