package com.umc.edison.remote.model.artletter

import com.google.gson.annotations.SerializedName
import com.umc.edison.data.model.ArtLetterMarkEntity
import com.umc.edison.remote.model.RemoteMapper

class PostArtLetterLikeResponse (
    @SerializedName("artletterId") val artletterId: Int,
    @SerializedName("likesCnt") val likesCnt: Int,
    @SerializedName("liked") val liked: Boolean,
) : RemoteMapper<ArtLetterMarkEntity> {

    override fun toData(): ArtLetterMarkEntity = ArtLetterMarkEntity(
        artletterId = artletterId,
        likesCnt = likesCnt,
        liked = liked,
    )
}
