package com.umc.edison.remote.model.mypage

import com.google.gson.annotations.SerializedName
import com.umc.edison.data.model.ArtletterEntity
import com.umc.edison.remote.model.RemoteMapper

data class GetScrapArtLettersByCategoryResponse(
    @SerializedName("artletterId") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("thumbnail") val thumbnail: String,
    @SerializedName("likesCnt") val likesCnt: Int,
    @SerializedName("scrapsCnt") val scrapsCnt: Int,
    @SerializedName("scrappedAt") val date: String,
) : RemoteMapper<ArtletterEntity> {
    override fun toData(): ArtletterEntity = ArtletterEntity(
        artletterId = id,
        title = title,
        thumbnail = thumbnail,
        likes = likesCnt,
        scraps = scrapsCnt,
    )
}
