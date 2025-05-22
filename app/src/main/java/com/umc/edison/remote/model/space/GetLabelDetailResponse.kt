package com.umc.edison.remote.model.space

import androidx.compose.ui.graphics.Color
import com.google.gson.annotations.SerializedName
import com.umc.edison.data.model.bubble.BubbleEntity
import com.umc.edison.data.model.label.LabelEntity
import com.umc.edison.remote.model.RemoteMapper
import com.umc.edison.remote.model.parseIso8601ToDate

data class GetLabelDetailResponse(
    @SerializedName("labelId") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("color") val color: Int,
    @SerializedName("bubbleCount") val bubbleCnt: Int,
    @SerializedName("bubbles") val bubbles: List<GetBubbleResponse>
) : RemoteMapper<LabelEntity> {
    override fun toData(): LabelEntity = LabelEntity(
        id = id,
        name = name,
        color = Color(color),
        bubbles = bubbles.map { it.toData() }
    )
}

data class GetBubbleResponse(
    @SerializedName("bubbleId") val id: Int,
    @SerializedName("title") val title: String?,
    @SerializedName("content") val content: String?,
    @SerializedName("mainImageUrl") val mainImageUrl: String?,
    @SerializedName("labels") val labels: List<GetLabelResponse>,
    @SerializedName("linkedBubbleId") val linkedBubbleId: List<Int>?,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("updatedAt") val updatedAt: String
) : RemoteMapper<BubbleEntity> {

    override fun toData(): BubbleEntity = BubbleEntity(
        id = id,
        title = title,
        content = content,
        mainImage = mainImageUrl,
        labels = labels.map { it.toData() },
        createdAt = parseIso8601ToDate(createdAt),
        updatedAt = parseIso8601ToDate(updatedAt)
    )

    data class GetLabelResponse(
        @SerializedName("labelId") val id: Int,
        @SerializedName("name") val name: String,
        @SerializedName("color") val color: Int
    ) : RemoteMapper<LabelEntity> {
        override fun toData(): LabelEntity = LabelEntity(
            id = id,
            name = name,
            color = Color(color),
            bubbles = emptyList()
        )
    }
}
