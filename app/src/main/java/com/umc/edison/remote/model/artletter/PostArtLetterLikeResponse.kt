package com.umc.edison.remote.model.artletter

import com.google.gson.annotations.SerializedName

class PostArtLetterLikeResponse (
    @SerializedName("artletterId") val artLetterId: Int,
    @SerializedName("likesCnt") val likesCnt: Int,
    @SerializedName("liked") val liked: Boolean,
)
