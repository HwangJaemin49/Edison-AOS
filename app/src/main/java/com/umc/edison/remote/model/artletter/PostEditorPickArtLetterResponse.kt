package com.umc.edison.remote.model.artletter

import com.google.gson.annotations.SerializedName
import com.umc.edison.data.model.ArtLetterDetailEntity
import com.umc.edison.data.model.ArtletterEntity
import com.umc.edison.remote.model.RemoteMapper
import java.util.Date


data class PostEditorPickArtLetterResponse(
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
    @SerializedName("liked") val liked : Boolean,
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
        createdAt = createdAt,
        updatedAt = updatedAt,
        liked = liked,
        scraped = scraped
    )
}

fun List<PostEditorPickArtLetterResponse>.toData(): List<ArtLetterDetailEntity> = map { it.toData() }
