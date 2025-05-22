package com.umc.edison.remote.model.sync

import com.google.gson.annotations.SerializedName
import com.umc.edison.data.model.bubble.BubbleEntity
import com.umc.edison.remote.model.toIso8601String

data class SyncBubbleRequest(
    @SerializedName("localIdx") val bubbleId: Int,
    @SerializedName("title") val title: String?,
    @SerializedName("content") val content: String?,
    @SerializedName("mainImageUrl") val mainImageUrl: String?,
    @SerializedName("backlinkIds") val backlinkIds: List<Int>,
    @SerializedName("labelIdxs") val labelIds: List<Int>,
    @SerializedName("isDeleted") val isDeleted: Boolean,
    @SerializedName("isTrashed") val isTrashed: Boolean,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("updatedAt") val updatedAt: String,
    @SerializedName("deletedAt") val deletedAt: String?
)

fun BubbleEntity.toSyncBubbleRequest(): SyncBubbleRequest = SyncBubbleRequest(
    bubbleId = id,
    title = title,
    content = content,
    mainImageUrl = mainImage,
    backlinkIds = if (linkedBubble != null) backLinks.map { it.id } + linkedBubble!!.id else backLinks.map { it.id },
    labelIds = labels.map { it.id },
    isDeleted = isDeleted,
    isTrashed = isTrashed,
    createdAt = createdAt.toIso8601String(),
    updatedAt = updatedAt.toIso8601String(),
    deletedAt = deletedAt?.toIso8601String()
)