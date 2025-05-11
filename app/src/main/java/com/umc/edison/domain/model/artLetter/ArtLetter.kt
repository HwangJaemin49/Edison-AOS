package com.umc.edison.domain.model.artLetter

data class ArtLetter (
    val artLetterId: Int,
    val title: String,
    val content: String,
    val category: String,
    val readTime: Int,
    val writer: String,
    val tags: List<String>,
    val thumbnail: String,
    val likesCnt: Int,
    val liked: Boolean,
    val scraped: Boolean,
) {
    companion object {
        val DEFAULT = ArtLetter(
            artLetterId = 0,
            title = "",
            content = "",
            category = "",
            readTime = 0,
            writer = "",
            tags = emptyList(),
            thumbnail = "",
            likesCnt = 0,
            liked = false,
            scraped = false
        )
    }
}
