package com.umc.edison.remote.model.sync

import androidx.compose.ui.graphics.Color
import com.umc.edison.data.model.BubbleEntity
import com.umc.edison.data.model.LabelEntity
import com.umc.edison.remote.model.RemoteMapper
import com.umc.edison.remote.model.parseIso8601ToDate

data class SyncBubbleResponse(
    val bubbleId: Int,
    val title: String,
    val content: String,
    val mainImageUrl: String,
    val labels: List<Label>,
    val backlinkIds: List<Int>,
    val isDeleted: Boolean,
    val isTrashed: Boolean,
    val createdAt: String,
    val updatedAt: String,
    val deletedAt: String?
) : RemoteMapper<BubbleEntity> {
    override fun toData(): BubbleEntity {
        return BubbleEntity(
            id = bubbleId,
            title = title,
            content = content,
            mainImage = mainImageUrl,
            labels = labels.map { it.toData() },
            backLinks = emptyList(),
            linkedBubble = null,
            isDeleted = isDeleted,
            createdAt = parseIso8601ToDate(createdAt),
            updatedAt = parseIso8601ToDate(updatedAt),
            deletedAt = deletedAt?.let { parseIso8601ToDate(it) }
        )
    }

    data class Label(
        val labelId: Int,
        val name: String,
        val color: Int,
    ) : RemoteMapper<LabelEntity> {
        override fun toData(): LabelEntity = LabelEntity(
            id = labelId,
            name = name,
            color = Color(color),
        )
    }
}
