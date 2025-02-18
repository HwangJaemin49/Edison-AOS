package com.umc.edison.data.model

import android.util.Log
import com.umc.edison.domain.model.Bubble
import com.umc.edison.domain.model.ContentBlock
import com.umc.edison.domain.model.ContentType
import java.util.Date

data class BubbleEntity(
    var id: Int,
    val title: String? = null,
    val content: String? = null,
    var mainImage: String? = null,
    var labels: List<LabelEntity> = emptyList(),
    var backLinks: List<BubbleEntity> = emptyList(),
    var linkedBubble: BubbleEntity? = null,
    val isDeleted: Boolean = false,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date(),
    val deletedAt: Date? = null
) : DataMapper<Bubble> {
    override fun toDomain(): Bubble {
        // Text 타입의 경우 앞에 %<TEXT>와 뒤에 </TEXT>%가 붙어있고
        // Image 타입의 경우 앞에 %<IMAGE>와 뒤에 </IMAGE>%가 붙어있음
        val contentBlocks = content?.split("%<")?.mapIndexed { _, s ->
            val type = when {
                s.startsWith("${ContentType.TEXT}>") -> ContentType.TEXT
                s.startsWith("${ContentType.IMAGE}>") -> ContentType.IMAGE
                else -> return@mapIndexed null
            }
            val content = when (type) {
                ContentType.TEXT -> s.substringAfter("${ContentType.TEXT}>").substringBefore("</${ContentType.TEXT}")
                ContentType.IMAGE -> s.substringAfter("${ContentType.IMAGE}>").substringBefore("</${ContentType.IMAGE}")
            }
            ContentBlock(type, content)
        }?.filterNotNull() ?: emptyList()

        return Bubble(
            id,
            title,
            contentBlocks,
            mainImage,
            labels.toDomain(),
            backLinks.toDomain(),
            linkedBubble?.toDomain(),
            updatedAt
        )
    }
}

fun BubbleEntity.same(other: BubbleEntity): Boolean {
    if (this.mainImage?.isEmpty() == true) this.mainImage = null
    if (other.mainImage?.isEmpty() == true) other.mainImage = null

    Log.d("BubbleEntity", "this: $this,\n other: $other")

    return id == other.id &&
            title == other.title &&
            content == other.content &&
            mainImage == other.mainImage &&
            labels.map { it.id } == other.labels.map { it.id } &&
            backLinks.map { it.id } == other.backLinks.map { it.id } &&
            isDeleted == other.isDeleted
}

fun Bubble.toData(): BubbleEntity = BubbleEntity(
    id = id,
    title = title,
    content = contentBlocks.joinToString(separator = "") {
        "%<${it.type}>${it.content}</${it.type}>%"
    },
    mainImage = mainImage,
    labels = labels.toData(),
    backLinks = backLinks.toData(),
    linkedBubble = linkedBubble?.toData(),
    updatedAt = date
)

fun List<Bubble>.toData(): List<BubbleEntity> = map { it.toData() }
