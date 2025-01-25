package com.umc.edison.presentation.label

import com.umc.edison.ui.theme.Gray300

data class LabelDetailState(
    val isLoading: Boolean,
    val label: LabelDetailModel,
    val bubbleEditMode: BubbleEditMode,
    val error: Throwable? = null,
) {
    companion object {
        val DEFAULT = LabelDetailState(
            isLoading = false,
            label = LabelDetailModel(
                id = 0,
                labelName = "",
                labelColor = Gray300,
                bubbles = listOf()
            ),
            bubbleEditMode = BubbleEditMode.NONE
        )
    }
}

enum class BubbleEditMode {
    NONE, MOVE, DELETE
}