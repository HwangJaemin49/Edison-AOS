package com.umc.edison.remote.model.mypage

import com.google.gson.annotations.SerializedName

data class GetScrapArtLettersByCategoryResponse(
    @SerializedName("artletterId") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("thumbnail") val thumbnail: String,
    @SerializedName("likesCnt") val likesCnt: Int,
    @SerializedName("scrapsCnt") val scrapsCnt: Int,
    @SerializedName("scrappedAt") val date: String,
)
