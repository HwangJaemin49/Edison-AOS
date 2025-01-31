package com.umc.edison.remote.model

import androidx.compose.ui.graphics.Color
import com.google.gson.annotations.SerializedName
import com.umc.edison.data.model.LabelEntity

data class SyncLabelResponse(
    @SerializedName("labelId") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("color") val color: Int,
    @SerializedName("isDeleted") val isDeleted: Boolean,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("updatedAt") val updatedAt: String,
    @SerializedName("deletedAt") val deletedAt: String?
) : RemoteMapper<LabelEntity> {
    override fun toData(): LabelEntity {
        return LabelEntity(
            id = id,
            name = name,
            color = Color(color),
            bubbles = emptyList(),
            isDeleted = isDeleted,
            createdAt = createdAt.toDate() ?: throw IllegalArgumentException("createdAt is null"),
            updatedAt = updatedAt.toDate() ?: throw IllegalArgumentException("updatedAt is null"),
            deletedAt = deletedAt?.toDate()
        )
    }
}
