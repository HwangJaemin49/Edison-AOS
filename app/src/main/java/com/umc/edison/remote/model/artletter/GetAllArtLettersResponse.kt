package com.umc.edison.remote.model.artletter

import com.google.gson.annotations.SerializedName
import com.umc.edison.data.model.ArtletterEntity
import com.umc.edison.remote.model.RemoteMapper

data class GetAllArtLettersResponse(
    @SerializedName("artletterId") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("thumbnail") val thumbnail: String,
    @SerializedName("likes") val likes: Int,
    @SerializedName("scraps") val scraps: Int,
) : RemoteMapper<ArtletterEntity> {

    override fun toData(): ArtletterEntity = ArtletterEntity(
        artletterId = id,
        title = title,
        thumbnail = thumbnail,
        likes = likes,
        scraps = scraps,
    )
}

//fun List<GetAllArtLettersResponse>.toData(): List<ArtletterEntity> = map { it.toData() }
