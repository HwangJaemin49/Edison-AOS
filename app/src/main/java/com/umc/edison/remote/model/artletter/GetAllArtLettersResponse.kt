package com.umc.edison.remote.model.artletter

import com.google.gson.annotations.SerializedName
import com.umc.edison.domain.model.ArtLetter

data class GetAllArtLettersResponse(
    @SerializedName("artletterId") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("thumbnail") val thumbnail: String?,
    @SerializedName("likes") val likes: Int,
    @SerializedName("scraps") val scraps: Int
) {
    fun toDomain() = ArtLetter(
        id = id,
        title = title,
        thumbnail = thumbnail ?: "",  // null 값 방지
        likes = likes,
        scraps = scraps
    )
}



