package com.umc.edison.remote.model

import androidx.compose.ui.graphics.Color
import com.google.gson.annotations.SerializedName
import com.umc.edison.data.model.BubbleEntity
import com.umc.edison.data.model.LabelEntity
import java.util.Date

data class GetLabelResponse(
    @SerializedName("labelId")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("color")
    val color: Int,
    @SerializedName("bubbleCount")
    val bubbleCnt: Int
) : RemoteMapper<LabelEntity> {
    override fun toData(): LabelEntity = LabelEntity(
        id = id,
        name = name,
        color = Color(color),
        bubbles = List(bubbleCnt) { BubbleEntity(id = 0, date = Date(), labels = emptyList()) }
    )
}
