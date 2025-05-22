package com.umc.edison.remote.model.sync

import androidx.compose.ui.graphics.Color
import com.google.gson.annotations.SerializedName
import com.umc.edison.data.model.label.LabelEntity
import com.umc.edison.remote.model.RemoteMapper
import com.umc.edison.remote.model.parseIso8601ToDate

data class SyncLabelResponse(
    @SerializedName("localIdx") val id: String,
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
            isDeleted = isDeleted,
            createdAt = parseIso8601ToDate(createdAt),
            updatedAt = parseIso8601ToDate(updatedAt),
            deletedAt = deletedAt?.let { parseIso8601ToDate(it) }
        )
    }
}
