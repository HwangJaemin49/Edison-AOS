package com.umc.edison.remote.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.google.gson.annotations.SerializedName
import com.umc.edison.data.model.LabelEntity

data class AddLabelRequest(
    @SerializedName("labelId") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("color") val color: Int
) : RemoteMapper<LabelEntity> {
    override fun toData(): LabelEntity {
        return LabelEntity(
            id = id,
            name = name,
            color = Color(color),
            bubbles = emptyList()
        )
    }
}

fun LabelEntity.toAddLabelLocal(): AddLabelRequest = AddLabelRequest(
    id = id,
    name = name,
    color = color.toArgb()
)
