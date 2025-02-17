package com.umc.edison.remote.model.artletter

import com.google.gson.annotations.SerializedName

data class GetSortedArtLettersRequest(
    @SerializedName("sortBy") val sortBy: String,
)