package com.umc.edison.remote.model.mypage

import com.google.gson.annotations.SerializedName
import com.umc.edison.data.model.identity.IdentityCategoryEntity
import com.umc.edison.data.model.identity.IdentityEntity
import com.umc.edison.data.model.identity.IdentityKeywordEntity
import com.umc.edison.remote.model.RemoteMapper

data class GetAllMyTestResultsResponse(
    @SerializedName("categories") val categories: CategoryType,
) {
    data class CategoryType(
        @SerializedName("CATEGORY1") val category1: List<Keyword>,
        @SerializedName("CATEGORY2") val category2: List<Keyword>,
        @SerializedName("CATEGORY3") val category3: List<Keyword>,
        @SerializedName("CATEGORY4") val category4: List<Keyword>,
    ) : RemoteMapper<List<IdentityEntity>> {
        override fun toData(): List<IdentityEntity> {
            val categories = listOf(
                IdentityCategoryEntity.EXPLAIN to category1,
                IdentityCategoryEntity.FIELD to category2,
                IdentityCategoryEntity.ENVIRONMENT to category3,
                IdentityCategoryEntity.INSPIRATION to category4,
            )

            return categories.map { (category, keywords) ->
                IdentityEntity(
                    category = category,
                    selectedKeywords = keywords.map { it.toData() },
                    keywords = emptyList()
                )
            }
        }

        data class Keyword(
            @SerializedName("keywordId") val id: Int,
            @SerializedName("keywordName") val name: String,
        ) : RemoteMapper<IdentityKeywordEntity> {
            override fun toData(): IdentityKeywordEntity = IdentityKeywordEntity(
                id = id,
                name = name,
            )
        }
    }
}
