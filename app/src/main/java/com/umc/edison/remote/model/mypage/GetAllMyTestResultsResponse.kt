package com.umc.edison.remote.model.mypage

import com.google.gson.annotations.SerializedName

data class GetAllMyTestResultsResponse(
    @SerializedName("categories") val categories: CategoryType,
) {
    data class CategoryType(
        @SerializedName("CATEGORY1") val category1: List<Keyword>,
        @SerializedName("CATEGORY2") val category2: List<Keyword>,
        @SerializedName("CATEGORY3") val category3: List<Keyword>,
        @SerializedName("CATEGORY4") val category4: List<Keyword>,
    ) {
        data class Keyword(
            @SerializedName("keywordId") val id: Int,
            @SerializedName("keywordName") val name: String,
        )
    }
}
