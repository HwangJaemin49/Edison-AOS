package com.umc.edison.remote.model.artletter

import com.google.gson.annotations.SerializedName
import com.umc.edison.data.model.ArtLetterMarkEntity
import com.umc.edison.data.model.ArtLetterScrapEntity
import com.umc.edison.remote.model.RemoteMapper

class PostArtLetterScrapResponse (
    @SerializedName("artletterId") val artletterId: Int,
    @SerializedName("scrapsCnt") val scrapsCnt: Int,
    @SerializedName("scrapped") val scrapped: Boolean,
) : RemoteMapper<ArtLetterScrapEntity> {

    override fun toData(): ArtLetterScrapEntity = ArtLetterScrapEntity(
        artletterId = artletterId,
        scrapsCnt = scrapsCnt,
        scrapped = scrapped,
    )
}
