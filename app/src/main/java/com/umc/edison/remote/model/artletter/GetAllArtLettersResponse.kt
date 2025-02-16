package com.umc.edison.remote.model.artletter

import android.util.Log
import com.google.gson.annotations.SerializedName
import com.umc.edison.data.model.ArtletterEntity
import com.umc.edison.remote.model.RemoteMapper

data class GetAllArtLettersResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("thumbnail") val thumbnail: String,
    @SerializedName("likes") val likes: Int,
    @SerializedName("scraps") val scraps: Int,
) : RemoteMapper<ArtletterEntity> {

    override fun toData(): ArtletterEntity {
        return ArtletterEntity(
            artletterId = id,
            title = title,
            thumbnail = thumbnail,
            likes = likes,
            scraps = scraps,
        )
    }
}
