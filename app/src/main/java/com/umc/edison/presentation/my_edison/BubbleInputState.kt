package com.umc.edison.presentation.my_edison

import com.umc.edison.presentation.model.BubbleModel
import com.umc.edison.presentation.model.LabelModel

data class BubbleInputState(
    val isLoading: Boolean = false,
    val bubble: BubbleModel,
    val labels: List<LabelModel> = emptyList(),
    val error: Throwable? = null,

    ) {
    companion object {
        val DEFAULT = BubbleInputState(
            isLoading = false,
            bubble = BubbleModel(),
            labels =  emptyList(),
            error = null
        )
    }
}