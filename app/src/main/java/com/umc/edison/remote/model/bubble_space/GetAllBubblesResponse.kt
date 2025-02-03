package com.umc.edison.remote.model.bubble_space

import androidx.compose.ui.graphics.Color
import com.google.gson.annotations.SerializedName
import com.umc.edison.data.model.BubbleEntity
import com.umc.edison.data.model.LabelEntity
import com.umc.edison.remote.model.RemoteMapper
import com.umc.edison.remote.model.toDate

data class GetAllBubblesResponse(
    @SerializedName("bubbleId") val bubbleId: Int,
    @SerializedName("title") val title: String,
    @SerializedName("content") val content: String,
    @SerializedName("mainImageUrl") val mainImageUrl: String,
    @SerializedName("labels") val labels: List<LabelResponse>,
    @SerializedName("linkedBubbleId") val linkedBubbleId: Int?,
    @SerializedName("createdAt") val linkedBubbleTitle: String?,
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
        date = updatedAt.toDate() ?: throw IllegalArgumentException("updatedAt is null"),
    )
}
