package com.umc.edison.remote.model.artletter

import com.google.gson.annotations.SerializedName

class PostArtLetterScrapResponse (
    @SerializedName("artletterId") val artLetterId: Int,
    @SerializedName("scrapsCnt") val scrapsCnt: Int,
    @SerializedName("scrapped") val scrapped: Boolean,
)
