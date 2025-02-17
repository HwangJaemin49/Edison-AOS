package com.umc.edison.remote.model.artletter

import com.google.gson.annotations.SerializedName
import com.umc.edison.data.model.ArtLetterDetailEntity
import com.umc.edison.remote.model.RemoteMapper
import com.umc.edison.remote.model.parseIso8601ToDate


class GetArtLetterDetailResponse (
    @SerializedName("artletterId") val artletterId: Int,
    @SerializedName("title") val title: String,
    @SerializedName("content") val content: String,
    @SerializedName("category") val category: String,
    @SerializedName("readTime") val readTime: Int,
    @SerializedName("writer") val writer: String,
    @SerializedName("tags") val tags: String,
    @SerializedName("thumbnail") val thumbnail: String?,
    @SerializedName("likesCnt") val likesCnt: Int,
    @SerializedName("scrapsCnt") val scrapsCnt: Int,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("updatedAt") val updatedAt: String,
    @SerializedName("liked") val liked: Boolean,
    @SerializedName("scraped") val scraped: Boolean
) : RemoteMapper<ArtLetterDetailEntity> {

    override fun toData(): ArtLetterDetailEntity = ArtLetterDetailEntity(
        artletterId = artletterId,
        title = title,
        content = content,
        category = category,
        readTime = readTime,
        writer = writer,
        tags = tags,
        thumbnail = thumbnail,
        likesCnt = likesCnt,
        scrapsCnt = scrapsCnt,
        createdAt = parseIso8601ToDate(createdAt),
        updatedAt = parseIso8601ToDate(updatedAt),
        liked = liked,
        scraped = scraped

    )
}
