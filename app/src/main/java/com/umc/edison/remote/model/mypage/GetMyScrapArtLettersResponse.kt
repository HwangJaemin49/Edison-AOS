package com.umc.edison.remote.model.mypage

import com.google.gson.annotations.SerializedName

data class GetMyScrapArtLettersResponse(
    @SerializedName("artletterId") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("thumbnail") val imageUrl: String,
    @SerializedName("scrapsCnt") val scrapCnt: Int,
    @SerializedName("scrappedAt") val scrapDate: String,
)
