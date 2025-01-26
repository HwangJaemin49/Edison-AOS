package com.umc.edison.presentation.label

import com.umc.edison.presentation.model.BubbleModel
import com.umc.edison.presentation.model.LabelModel
import com.umc.edison.ui.theme.Gray300

data class LabelDetailState(
    val isLoading: Boolean,
    val label: LabelModel,
    val selectedBubbles: List<BubbleModel> = listOf(),
    val bubbleEditMode: BubbleEditMode,
    val movableLabels: List<LabelModel> = listOf(),
    val error: Throwable? = null,
) {
    companion object {
        val DEFAULT = LabelDetailState(
            isLoading = false,
            label = LabelModel(
                id = 0,
                name = "",
                color = Gray300,
                bubbles = listOf()
            ),
            bubbleEditMode = BubbleEditMode.NONE
        )
    }
}

enum class BubbleEditMode {
    NONE, VIEW, EDIT, MOVE, DELETE
}