package com.umc.edison.data.model

import com.umc.edison.data.DataMapper
import com.umc.edison.domain.model.Bubble
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
                s.startsWith("${ContentType.TEXT}>%") -> ContentType.TEXT
                s.startsWith("${ContentType.IMAGE}>%") -> ContentType.IMAGE
                else -> return@mapIndexed null
            }
            val content = s.substringAfter(">").substringBefore("%</")
            Bubble.BubbleContentBlock(type, content, index)
        }?.filterNotNull() ?: emptyList()

        return Bubble(id, title, contentBlocks, mainImage, labels.map { it.toDomain() }, date)
    }
}

fun Bubble.toData(): BubbleEntity = BubbleEntity(
    id = id,
    title = title,
    content = contentBlocks.joinToString(separator = "") {
        "%<${it.type}>${it.content}%</${it.type}>"
    },
    mainImage = mainImage,
    labels = labels.map { it.toData() },
    date = date
)

fun List<Bubble>.toData(): List<BubbleEntity> = map { it.toData() }
