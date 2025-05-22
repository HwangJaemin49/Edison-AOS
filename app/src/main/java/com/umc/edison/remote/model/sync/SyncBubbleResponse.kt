package com.umc.edison.remote.model.sync

import androidx.compose.ui.graphics.Color
import com.google.gson.annotations.SerializedName
import com.umc.edison.data.model.bubble.BubbleEntity
import com.umc.edison.data.model.label.LabelEntity
import com.umc.edison.remote.model.RemoteMapper
import com.umc.edison.remote.model.parseIso8601ToDate

data class SyncBubbleResponse(
    @SerializedName("localIdx") val bubbleId: Int,
    @SerializedName("title") val title: String,
    @SerializedName("content") val content: String,
    @SerializedName("mainImageUrl") val mainImageUrl: String,
    @SerializedName("labels") val labels: List<Label>,
    @SerializedName("backlinkIdxs") val backlinkIds: List<Int>,
    @SerializedName("isDeleted") val isDeleted: Boolean,
    @SerializedName("isTrashed") val isTrashed: Boolean,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("updatedAt") val updatedAt: String,
    @SerializedName("deletedAt") val deletedAt: String?
) : RemoteMapper<BubbleEntity> {
    override fun toData(): BubbleEntity {
        return BubbleEntity(
            id = bubbleId,
            title = title,
            content = content,
            mainImage = mainImageUrl,
            labels = labels.map { it.toData() },
            backLinks = backlinkIds.map { BubbleEntity(it) },
            linkedBubble = null,
            isDeleted = isDeleted,
            isTrashed = isTrashed,
            createdAt = parseIso8601ToDate(createdAt),
            updatedAt = parseIso8601ToDate(updatedAt),
            deletedAt = deletedAt?.let { parseIso8601ToDate(it) }
        )
    }

    data class Label(
        @SerializedName("localIdx") val labelId: Int,
        @SerializedName("name") val name: String,
        @SerializedName("color") val color: Int,
    ) : RemoteMapper<LabelEntity> {
        override fun toData(): LabelEntity = LabelEntity(
            id = labelId,
            name = name,
            color = Color(color),
        )
    }
}
