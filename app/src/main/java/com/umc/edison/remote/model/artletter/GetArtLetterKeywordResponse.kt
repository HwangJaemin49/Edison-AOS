package com.umc.edison.remote.model.artletter

import com.google.gson.annotations.SerializedName

class GetArtLetterKeywordResponse (
    @SerializedName("artletterId") val artletterId: Int,
    @SerializedName("keyword") val keyword: String
)