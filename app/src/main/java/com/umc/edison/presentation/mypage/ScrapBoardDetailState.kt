package com.umc.edison.presentation.mypage

import com.umc.edison.presentation.model.ArtLetterPreviewModel

data class ScrapBoardDetailState(
    val categoryName: String,
    val artLetters: List<ArtLetterPreviewModel>
) {
    companion object {
        val DEFAULT = ScrapBoardDetailState(
            categoryName = "",
            artLetters = emptyList()
        )
    }
}
