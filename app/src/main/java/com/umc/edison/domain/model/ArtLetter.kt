package com.umc.edison.domain.model

data class ArtLetter(
    val artletterId: Int,
    val title: String,
    val thumbnail: String?,
    val likes: Int,
    val scraps: Int,
)
