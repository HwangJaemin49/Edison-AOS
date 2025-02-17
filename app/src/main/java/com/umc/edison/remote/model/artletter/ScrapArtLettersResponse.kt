package com.umc.edison.remote.model.artletter

import com.google.gson.annotations.SerializedName
import com.umc.edison.data.model.ArtletterEntity

data class ScrapArtLettersResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: ScrapArtLettersResult
)

data class ScrapArtLettersResult(
    @SerializedName("artletterId") val artLetterId: Int,
    @SerializedName("scrapsCnt") val scrapsCount: Int,
    @SerializedName("isScrapped") val isScrapped: Boolean
)

