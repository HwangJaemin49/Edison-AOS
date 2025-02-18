package com.umc.edison.presentation.model

import com.umc.edison.domain.model.ArtLetterScrap
data class ArtLetterScrapModel (
    val artletterId: Int,
    val scrapsCnt: Int,
    val scrapped: Boolean,
) {
    companion object {
        val DEFAULT = ArtLetterScrapModel(
            artletterId = 1,
            scrapsCnt = 0,
            scrapped = false,
        )
    }

    fun toDomain(): ArtLetterScrap {
        return ArtLetterScrap(
            artletterId = artletterId,
            scrapsCnt = scrapsCnt,
            scrapped = scrapped,
        )
    }
}

fun ArtLetterScrap.toPresentation(): ArtLetterScrapModel {
    return ArtLetterScrapModel(
        artletterId = artletterId,
        scrapsCnt = scrapsCnt,
        scrapped = scrapped,
    )
}
