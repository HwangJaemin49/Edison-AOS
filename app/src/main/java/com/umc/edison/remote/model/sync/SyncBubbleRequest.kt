package com.umc.edison.remote.model.sync

import com.google.gson.annotations.SerializedName

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
