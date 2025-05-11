package com.umc.edison.data.model.bubble

import android.util.Log
import com.umc.edison.data.model.DataMapper
import com.umc.edison.data.model.label.LabelEntity
import com.umc.edison.data.model.label.toData
import com.umc.edison.data.model.toDomain
import com.umc.edison.domain.model.bubble.Bubble
import java.util.Date

data class BubbleEntity(
    var id: String,
    val title: String?,
    val content: String?,
    var mainImage: String?,
    var labels: List<LabelEntity>,
    var backLinks: List<BubbleEntity>,
    var linkedBubble: BubbleEntity?,
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
}

fun BubbleEntity.same(other: BubbleEntity): Boolean {
    if (this.mainImage?.isEmpty() == true) this.mainImage = null
    if (other.mainImage?.isEmpty() == true) other.mainImage = null

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
