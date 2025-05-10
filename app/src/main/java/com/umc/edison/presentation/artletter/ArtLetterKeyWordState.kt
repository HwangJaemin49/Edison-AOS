package com.umc.edison.presentation.artletter

import com.umc.edison.presentation.model.ArtLetterKeyWordModel

data class ArtLetterKeyWordState(
    val keywords: List<ArtLetterKeyWordModel>,
) {
    companion object {
        val DEFAULT = ArtLetterKeyWordState(
            keywords = emptyList()
        )
    }
}
