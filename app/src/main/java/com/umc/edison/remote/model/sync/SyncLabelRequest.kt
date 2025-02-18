package com.umc.edison.remote.model.sync

import androidx.compose.ui.graphics.toArgb
import com.google.gson.annotations.SerializedName
import com.umc.edison.data.model.LabelEntity
import com.umc.edison.remote.model.toIso8601String

data class SyncLabelRequest(
    @SerializedName("localIdx") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("color") val color: Int,
    @SerializedName("isDeleted") val isDeleted: Boolean,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("updatedAt") val updatedAt: String,
    @SerializedName("deletedAt") val deletedAt: String?
)

fun LabelEntity.toSyncLabelRequest(): SyncLabelRequest = SyncLabelRequest(
    id = id,
    name = name,
    color = color.toArgb(),
    isDeleted = false,
    createdAt = createdAt.toIso8601String(),
    updatedAt = updatedAt.toIso8601String(),
    deletedAt = deletedAt?.toIso8601String()
)
