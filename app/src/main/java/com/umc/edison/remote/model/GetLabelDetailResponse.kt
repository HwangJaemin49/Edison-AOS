package com.umc.edison.remote.model

import androidx.compose.ui.graphics.Color
import com.google.gson.annotations.SerializedName
import com.umc.edison.data.model.BubbleEntity
import com.umc.edison.data.model.LabelEntity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

data class GetLabelDetailResponse(
    @SerializedName("labelId") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("color") val color: Int,
    @SerializedName("bubbleCount") val bubbleCnt: Int,
    @SerializedName("bubbles") val bubbles: List<GetBubbleResponse>
) : RemoteMapper<LabelEntity> {
    override fun toData(): LabelEntity = LabelEntity(
        id = id,
        name = name,
        color = Color(color),
        bubbles = bubbles.map { it.toData() }
    )
}

data class GetBubbleResponse(
    @SerializedName("bubbleId") val id: Int,
    @SerializedName("title") val title: String?,
    @SerializedName("content") val content: String?,
    @SerializedName("mainImageUrl") val mainImageUrl: String?,
    @SerializedName("labels") val labels: List<GetLabelResponse>,
    @SerializedName("linkedBubbleId") val linkedBubbleId: List<Int>?,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("updatedAt") val updatedAt: String
) : RemoteMapper<BubbleEntity> {

    override fun toData(): BubbleEntity = BubbleEntity(
        id = id,
        title = title,
        content = content,
        mainImage = mainImageUrl,
        labels = labels.map { it.toData() },
        date = parseIso8601ToDate(updatedAt)
    )

    data class GetLabelResponse(
        @SerializedName("labelId") val id: Int,
        @SerializedName("name") val name: String,
        @SerializedName("color") val color: Int
    ) : RemoteMapper<LabelEntity> {
        override fun toData(): LabelEntity = LabelEntity(
            id = id,
            name = name,
            color = Color(color),
            bubbles = emptyList()
        )
    }

    companion object {
        private fun parseIso8601ToDate(isoDateTime: String): Date {
            return try {
                val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault())
                simpleDateFormat.timeZone = TimeZone.getTimeZone("UTC")
                simpleDateFormat.parse(isoDateTime)!!
            } catch (e: Exception) {
                e.printStackTrace()
                Date()
            }
        }
    }
}
