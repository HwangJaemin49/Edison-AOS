package com.umc.edison.remote.model.sync

import androidx.compose.ui.graphics.Color
import com.google.gson.annotations.SerializedName
import com.umc.edison.data.model.label.LabelEntity
import com.umc.edison.remote.model.RemoteMapper

data class SyncBubbleResponse(
    @SerializedName("localIdx") val bubbleId: String,
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
) {
    data class Label(
        @SerializedName("localIdx") val labelId: String,
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
