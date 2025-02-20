package com.umc.edison.remote.model.space

import androidx.compose.ui.graphics.Color
import com.google.gson.annotations.SerializedName
import com.umc.edison.data.model.BubbleEntity
import com.umc.edison.data.model.LabelEntity
import com.umc.edison.remote.model.RemoteMapper
import com.umc.edison.remote.model.parseIso8601ToDate

data class GetLabelResponse(
    @SerializedName("localIdx")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("color")
    val color: Int,
    @SerializedName("bubbleCount")
    val bubbleCnt: Int,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("updatedAt")
    val updatedAt: String,
) : RemoteMapper<LabelEntity> {
    override fun toData(): LabelEntity = LabelEntity(
        id = id,
        name = name,
        color = Color(color),
        bubbles = List(bubbleCnt) { BubbleEntity(0) },
        isSynced = true,
        createdAt = parseIso8601ToDate(createdAt),
        updatedAt = parseIso8601ToDate(updatedAt),
    )
}
