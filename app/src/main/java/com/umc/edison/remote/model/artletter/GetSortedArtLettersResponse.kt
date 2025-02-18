package com.umc.edison.remote.model.artletter

import com.google.gson.annotations.SerializedName
import com.umc.edison.data.model.ArtletterEntity
import com.umc.edison.remote.model.RemoteMapper
import com.umc.edison.remote.model.parseIso8601ToDate

data class GetSortedArtLettersResponse(
    @SerializedName("artletterId") val artletterId: Int,
    @SerializedName("title") val title: String,
    @SerializedName("thumbnail") val thumbnail: String,
    @SerializedName("likesCnt") val likesCnt: Int,
    @SerializedName("scrapsCnt") val scrapsCnt: Int,
    @SerializedName("updatedAt") val updatedAt: String,
    @SerializedName("liked") val liked: Boolean,
    @SerializedName("scraped") val scraped: Boolean,
) : RemoteMapper<ArtletterEntity> {

    override fun toData(): ArtletterEntity {
        return ArtletterEntity(
            artletterId = artletterId,
            title = title,
            thumbnail = thumbnail,
            likesCnt = likesCnt,
            scrapsCnt = scrapsCnt,
            updatedAt = parseIso8601ToDate(updatedAt),
            liked = liked,
            scraped = scraped
        )
    }
}

fun List<GetSortedArtLettersResponse>.toData(): List<ArtletterEntity> = map { it.toData() }
