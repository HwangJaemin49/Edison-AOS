package com.umc.edison.remote.model.artletter

import com.google.gson.annotations.SerializedName
import com.umc.edison.data.model.ArtLetterPreviewEntity
import com.umc.edison.remote.model.RemoteMapper


data class GetEditorPickArtLetterResponse(
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
) : RemoteMapper<ArtLetterPreviewEntity> {
    override fun toData(): ArtLetterPreviewEntity = ArtLetterPreviewEntity(
        artLetterId = artletterId,
        title = title,
        thumbnail = thumbnail ?: "",
        scraped = scraped
    )
}

fun List<GetEditorPickArtLetterResponse>.toData(): List<ArtLetterPreviewEntity> = map { it.toData() }
