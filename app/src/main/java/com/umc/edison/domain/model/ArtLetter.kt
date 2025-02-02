package com.umc.edison.domain.model

data class ArtLetter(
    val id: Int,
    val title: String,
    val thumbnail: String,
    val content: String,
    val category: ArtLetterCategory,
    val likeCnt: Int,
    val scrapCnt: Int,
    val isLike: Boolean,
    val isScrapped: Boolean,
)