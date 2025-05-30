package com.umc.edison.remote.model.mypage

import com.google.gson.annotations.SerializedName
import com.umc.edison.data.model.artLetter.ArtLetterPreviewEntity
import com.umc.edison.remote.model.RemoteMapper

data class GetMyScrapArtLettersResponse(
    @SerializedName("category") val category: String,
    @SerializedName("artletters") val artLetters: List<ArtLetter>,
) : RemoteMapper<ArtLetterPreviewEntity> {
    data class ArtLetter(
        @SerializedName("artletterId") val id: Int,
        @SerializedName("title") val title: String,
        @SerializedName("thumbnail") val thumbnail: String,
        @SerializedName("likesCnt") val likesCnt: Int,
        @SerializedName("scrapsCnt") val scrapsCnt: Int,
        @SerializedName("scrappedAt") val date: String,
    )

    override fun toData(): ArtLetterPreviewEntity = ArtLetterPreviewEntity(
        artLetterId = artLetters[0].id,
        category = category,
        title = artLetters[0].title,
        thumbnail = artLetters[0].thumbnail,
        scraped = true,
        tags = emptyList()
    )
}
