package com.umc.edison.remote.model.artletter

import com.google.gson.annotations.SerializedName

data class GetArtLetterCetegoryResponse (
    @SerializedName("categories") val categories: List<String>
)