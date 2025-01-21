package com.umc.edison.data.model

import com.umc.edison.data.DataMapper
import com.umc.edison.domain.model.Bubble
import com.umc.edison.domain.model.ContentBlock
import com.umc.edison.domain.model.ContentType

data class BubbleEntity(
    val id: Int,
    val title: String? = null,
    val content: String? = null,
    var mainImage: String? = null,
    var labels: List<LabelEntity>,
    val date: Long,
) : DataMapper<Bubble> {
    override fun toDomain(): Bubble {
        // Text 타입의 경우 앞에 %<TEXT>%와 뒤에 %</TEXT>%가 붙어있고
        // Image 타입의 경우 앞에 %<IMAGE>%와 뒤에 %</IMAGE>%가 붙어있음
        val contentBlocks = content?.split("%<")?.mapIndexed { index, s ->
            val type = when {
                s.startsWith("TEXT>%") -> ContentType.TEXT
                s.startsWith("IMAGE>%") -> ContentType.IMAGE
                else -> return@mapIndexed null
            }
            val content = s.substringAfter(">").substringBefore("%</")
            ContentBlock(type, content, index)
        }?.filterNotNull() ?: emptyList()

        return Bubble(id, title, contentBlocks, mainImage, labels.map { it.toDomain() }, date)
    }
}

fun Bubble.toEntity(): BubbleEntity = BubbleEntity(
    id = id,
    title = title,
    content = contentBlocks.joinToString(separator = "") {
        "%<${it.type}>${it.content}%</${it.type}>"
    },
    mainImage = mainImage,
    labels = labels.map { it.toEntity() },
    date = date
)

fun List<Bubble>.toEntity(): List<BubbleEntity> = map { it.toEntity() }
