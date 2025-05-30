package com.umc.edison.presentation.mypage

import com.umc.edison.presentation.model.ArtLetterCategoryModel

data class ScrapBoardState(
    val myArtLetterCategories: List<ArtLetterCategoryModel>,
) {
    companion object {
        val DEFAULT = ScrapBoardState(
            myArtLetterCategories = emptyList()
        )
    }
}
