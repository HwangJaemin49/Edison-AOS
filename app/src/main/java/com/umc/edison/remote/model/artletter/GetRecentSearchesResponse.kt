package com.umc.edison.remote.model.artletter

import com.google.gson.annotations.SerializedName

data class GetRecentSearchesResponse(
    @SerializedName("keywords") val keywords: List<String>
)
