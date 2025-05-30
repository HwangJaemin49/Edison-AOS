package com.umc.edison.data.model.bubble

import android.util.Log
import com.umc.edison.data.model.DataMapper
import com.umc.edison.data.model.label.LabelEntity
import com.umc.edison.data.model.label.toData
import com.umc.edison.data.model.toDomain
import com.umc.edison.domain.model.bubble.Bubble
import java.util.Date

data class BubbleEntity(
    val id: String,
    val title: String?,
    val content: String?,
    val mainImage: String?,
    val labels: List<LabelEntity>,
    val backLinks: List<BubbleEntity>,
    val linkedBubble: BubbleEntity?,
    val isDeleted: Boolean = false,
    val isTrashed: Boolean = false,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date(),
    val deletedAt: Date? = null,
    val isSynced: Boolean = false
) : DataMapper<Bubble> {
    override fun toDomain(): Bubble = Bubble(
        id,
        title,
        content,
        mainImage,
        labels.toDomain(),
        backLinks.toDomain(),
        linkedBubble?.toDomain(),
        updatedAt
    )

    fun same(other: BubbleEntity): Boolean {
        Log.d("BubbleEntity", "this: $this,\nother: $other")

        return id == other.id &&
                title == other.title &&
                content == other.content &&
                mainImage == other.mainImage &&
                labels.map { it.id } == other.labels.map { it.id } &&
                backLinks.map { it.id } == other.backLinks.map { it.id } &&
                isDeleted == other.isDeleted &&
                isTrashed == other.isTrashed
    }
}

fun Bubble.toData(): BubbleEntity = BubbleEntity(
    id = id,
    title = title,
    content = content,
    mainImage = mainImage,
    labels = labels.toData(),
    backLinks = backLinks.toData(),
    linkedBubble = linkedBubble?.toData(),
    updatedAt = date
)

fun List<Bubble>.toData(): List<BubbleEntity> = map { it.toData() }
