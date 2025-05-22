package com.umc.edison.remote.model.space

import androidx.compose.ui.graphics.Color
import com.google.gson.annotations.SerializedName
import com.umc.edison.data.model.bubble.BubbleEntity
import com.umc.edison.data.model.label.LabelEntity
import com.umc.edison.remote.model.RemoteMapper
import com.umc.edison.remote.model.parseIso8601ToDate

data class GetAllBubblesResponse(
    @SerializedName("localIdx") val bubbleId: Int,
    @SerializedName("title") val title: String,
    @SerializedName("content") val content: String,
    @SerializedName("mainImageUrl") val mainImageUrl: String,
    @SerializedName("labels") val labels: List<LabelResponse>,
    @SerializedName("backlinkIdxs") val backLinks: List<Int>,
    @SerializedName("isDeleted") val isDeleted: Boolean?,
    @SerializedName("isTrashed") val isTrashed: Boolean,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("updatedAt") val updatedAt: String,
    @SerializedName("deletedAt") val deletedAt: String?
) : RemoteMapper<BubbleEntity> {

    data class LabelResponse(
        @SerializedName("localIdx") val labelId: Int,
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
        backLinks = backLinks.map { BubbleEntity(it) },
        isDeleted = isDeleted ?: false,
        isTrashed = isTrashed,
        createdAt = parseIso8601ToDate(createdAt),
        updatedAt = parseIso8601ToDate(updatedAt),
        deletedAt = deletedAt?.let { parseIso8601ToDate(it) },
        isSynced = true
    )
}
