package com.umc.edison.remote.model.artletter

import com.google.gson.annotations.SerializedName

data class GetArtLetterCategoryResponse (
    @SerializedName("categories") val categories: List<String>
)