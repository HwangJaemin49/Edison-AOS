package com.umc.edison.presentation.my_edison

import com.umc.edison.presentation.label.LabelEditMode
import com.umc.edison.presentation.model.BubbleModel
import com.umc.edison.presentation.model.LabelModel
import com.umc.edison.ui.theme.Gray300

data class BubbleInputState(
    val isLoading: Boolean = false,
    val bubble: BubbleModel,
    val bubbles : List<BubbleModel>,
    val labels: List<LabelModel>,
    val labelEditMode: LabelEditMode = LabelEditMode.NONE,
    val selectedLabel: LabelModel,
    val error: Throwable? = null,

    ) {
    companion object {
        val DEFAULT = BubbleInputState(
            isLoading = false,
            bubble = BubbleModel(),
            bubbles = emptyList(),
            labels =  emptyList(),
            labelEditMode = LabelEditMode.NONE,
            selectedLabel = LabelModel(
                id = 0,
                name = "",
                color = Gray300,
                bubbles = listOf()
            ),
            error = null
        )
    }
}