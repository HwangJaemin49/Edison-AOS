package com.umc.edison.remote.model.mypage

import androidx.compose.ui.graphics.Color
import com.google.gson.annotations.SerializedName
import com.umc.edison.data.model.bubble.BubbleEntity
import com.umc.edison.data.model.label.LabelEntity
import com.umc.edison.remote.model.RemoteMapper
import java.util.Calendar

data class GetDeletedBubbleListResponse(
    @SerializedName("bubbleId")
    val bubbleId: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("mainImageUrl")
    val mainImageUrl: String,
    @SerializedName("labels")
    val labels: List<LabelResponse>,
    @SerializedName("linkedBubbleId")
    val linkedBubbleId: Int,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("updatedAt")
    val updatedAt: String,
    @SerializedName("remainDay")
    val remainDay: Int,
) : RemoteMapper<BubbleEntity> {

    data class LabelResponse(
        @SerializedName("labelId")
        val labelId: Int,
        @SerializedName("name")
        val name: String,
        @SerializedName("color")
        val color: Int,
    ) : RemoteMapper<LabelEntity> {
        override fun toData(): LabelEntity = LabelEntity(
            id = labelId,
            name = name,
            color = Color(color)
        )
    }

    override fun toData(): BubbleEntity {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, -(30 - remainDay))

        return BubbleEntity(
            id = bubbleId,
            title = title,
            content = content,
            mainImage = mainImageUrl,
            updatedAt = calendar.time
        )
    }
}
