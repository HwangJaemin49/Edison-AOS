package com.umc.edison.remote.model.bubble

import androidx.compose.ui.graphics.Color
import com.google.gson.annotations.SerializedName
import com.umc.edison.data.model.bubble.BubbleEntity
import com.umc.edison.data.model.label.LabelEntity
import com.umc.edison.remote.model.RemoteMapper
import com.umc.edison.remote.model.parseIso8601ToDate

data class AddBubbleResponse(
    @SerializedName("localIdx") val id: String,
    @SerializedName("title") val title: String?,
    @SerializedName("content") val content: String?,
    @SerializedName("mainImageUrl") val mainImageUrl: String?,
    @SerializedName("labels") val labels: List<LabelResponse>,
    @SerializedName("backlinkIdxs") val backlinkIds: List<String>,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("updatedAt") val updatedAt: String
) : RemoteMapper<BubbleEntity> {
    data class LabelResponse(
        @SerializedName("localIdx") val id: String,
        @SerializedName("name") val name: String,
        @SerializedName("color") val color: Int,
    ) : RemoteMapper<LabelEntity> {
        override fun toData(): LabelEntity {
            return LabelEntity(
                id = id,
                name = name,
                color = Color(color),
            )
        }
    }

    override fun toData(): BubbleEntity {
        return BubbleEntity(
            id = id,
            title = title,
            content = content,
            mainImage = mainImageUrl,
            labels = labels.map { it.toData() },
            backLinks = backlinkIds.map { BubbleEntity(id = it, title = null, content = null, mainImage = null, labels = emptyList(), backLinks = emptyList(), linkedBubble = null) },
            linkedBubble = null,
            createdAt = parseIso8601ToDate(createdAt),
            updatedAt = parseIso8601ToDate(updatedAt)
        )
    }
}
