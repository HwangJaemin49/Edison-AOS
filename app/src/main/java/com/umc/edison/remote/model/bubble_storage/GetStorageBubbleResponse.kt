package com.umc.edison.remote.model.bubble_storage

import androidx.compose.ui.graphics.Color
import com.google.gson.annotations.SerializedName
import com.umc.edison.data.model.BubbleEntity
import com.umc.edison.data.model.LabelEntity
import com.umc.edison.remote.model.RemoteMapper
import com.umc.edison.remote.model.parseIso8601ToDate

class GetStorageBubbleResponse(
    @SerializedName("bubbleId") val bubbleId: Int,
    @SerializedName("title") val title: String,
    @SerializedName("content") val content: String,
    @SerializedName("mainImageUrl") val mainImageUrl: String,
    @SerializedName("labels") val labels: List<LabelResponse>,
    @SerializedName("linkedBubbleId") val linkedBubbleId: Int?,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("updatedAt") val updatedAt: String
) : RemoteMapper<BubbleEntity> {

    data class LabelResponse(
        @SerializedName("labelId") val labelId: Int,
        @SerializedName("name") val name: String,
        @SerializedName("color") val color: Int
    ) : RemoteMapper<LabelEntity> {
        override fun toData(): LabelEntity = LabelEntity(
            id = labelId,
            name = name,
            color = Color(color),
            bubbles = emptyList()
        )
    }

    override fun toData(): BubbleEntity = BubbleEntity(
        id = bubbleId,
        title = title,
        content = content,
        mainImage = mainImageUrl,
        labels = labels.map { it.toData() },
        createdAt = parseIso8601ToDate(createdAt),
        updatedAt = parseIso8601ToDate(updatedAt),
    )
}
