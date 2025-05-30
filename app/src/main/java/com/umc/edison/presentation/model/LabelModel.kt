package com.umc.edison.presentation.model

import androidx.compose.ui.graphics.Color
import com.umc.edison.domain.model.label.Label
import com.umc.edison.ui.theme.Gray300
import com.umc.edison.ui.theme.White000
import java.util.UUID

data class LabelModel(
    val id: String?,
    val name: String,
    val color: Color,
    val bubbleCnt: Int,
) {
    fun toDomain(): Label = Label(
        id = id ?: UUID.randomUUID().toString(),
        name = name,
        color = color
    )

    companion object {
        val DEFAULT = LabelModel(
            id = null,
            name = "-",
            color = White000,
            bubbleCnt = 0
        )

        val INIT = LabelModel(
            id = null,
            name = "",
            color = Gray300,
            bubbleCnt = 0
        )
    }
}

fun Label.toPresentation(): LabelModel = LabelModel(id, name, color, 0)

fun List<Label>.toPresentation(): List<LabelModel> = map { it.toPresentation() }
