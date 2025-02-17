package com.umc.edison.remote.model.space

import com.google.gson.annotations.SerializedName

data class GetBubblePositionResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("x") val x: Float,
    @SerializedName("y") val y: Float,
    @SerializedName("group") val group: Int
)
