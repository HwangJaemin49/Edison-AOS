package com.umc.edison.remote.model.mypage

import com.google.gson.annotations.SerializedName
import com.umc.edison.data.model.ArtLetterCategoryEntity
import com.umc.edison.remote.model.RemoteMapper

data class GetMyScrapArtLettersResponse(
    @SerializedName("category") val category: String,
    @SerializedName("artLetters") val artLetters: List<ArtLetter>,
) : RemoteMapper<ArtLetterCategoryEntity> {
    data class ArtLetter(
        @SerializedName("artLetterId") val id: Int,
        @SerializedName("title") val title: String,
        @SerializedName("thumbnail") val thumbnail: String,
        @SerializedName("likesCnt") val likesCnt: Int,
        @SerializedName("scrapsCnt") val scrapsCnt: Int,
        @SerializedName("scrappedAt") val date: String,
    )

    override fun toData(): ArtLetterCategoryEntity = ArtLetterCategoryEntity(
        name = category,
        thumbnail = artLetters.first().thumbnail,
    )
}
