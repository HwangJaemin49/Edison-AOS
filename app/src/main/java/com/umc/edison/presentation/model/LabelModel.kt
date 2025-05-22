package com.umc.edison.presentation.model

import androidx.compose.ui.graphics.Color
import com.umc.edison.domain.model.label.Label
import com.umc.edison.ui.theme.Gray300
import java.util.UUID

data class LabelModel(
    val id: String?,
    val name: String,
    val color: Color
) {
    fun toDomain(): Label = Label(
        id = id ?: UUID.randomUUID().toString(),
        name = name,
        color = color
    )

    companion object {
        val DEFAULT = LabelModel(
            id = null,
            name = "",
            color = Gray300
        )
    }
}

fun Label.toPresentation(): LabelModel = LabelModel(id, name, color)

fun List<Label>.toPresentation(): List<LabelModel> = map { it.toPresentation() }
