package com.umc.edison.data.model


import com.umc.edison.domain.model.ArtLetterScrap

data class ArtLetterScrapEntity(
    val artletterId: Int,
    val scrapsCnt: Int,
    val scrapped: Boolean
) : DataMapper<ArtLetterScrap> {
    override fun toDomain(): ArtLetterScrap {
        return ArtLetterScrap(
            artletterId = artletterId,
            scrapsCnt = scrapsCnt,
            scrapped = scrapped
        )
    }
}

fun ArtLetterScrap.toData(): ArtLetterScrapEntity {
    return ArtLetterScrapEntity(
        artletterId = artletterId,
        scrapsCnt = scrapsCnt,
        scrapped = scrapped
    )
}