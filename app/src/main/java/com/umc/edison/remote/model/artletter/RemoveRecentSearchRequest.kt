package com.umc.edison.remote.model.artletter

import com.google.gson.annotations.SerializedName

data class RemoveRecentSearchRequest(
    @SerializedName("keyword") val keyword: String
)
