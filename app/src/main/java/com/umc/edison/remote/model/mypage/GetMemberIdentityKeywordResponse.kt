package com.umc.edison.remote.model.mypage

import com.google.gson.annotations.SerializedName

data class GetMemberIdentityKeywordResponse(
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

enum class IdentityCategory(val fieldName: String, val question: String) {
    CATEGORY1("CATEGORY1", "나를 설명하는 단어를 골라주세요."),
    CATEGORY2("CATEGORY2", "지금, 혹은 미래의 나는 어떤 필드 위에 서 있나요?"),
    CATEGORY3("CATEGORY3", "나에게 가장 영감을 주는 환경은?"),
    CATEGORY4("CATEGORY4", "당신의 상상력을 자극하는 분야를 골라주세요.");

    companion object {
        fun fromFieldName(fieldName: String): IdentityCategory? {
            return entries.find { it.fieldName == fieldName }
        }
    }
}

fun getCategoryQuestion(categoryField: String): String {
    return IdentityCategory.fromFieldName(categoryField)!!.question
}
