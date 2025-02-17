package com.umc.edison.presentation.label

import com.umc.edison.presentation.baseBubble.BaseBubbleMode
import com.umc.edison.presentation.baseBubble.BaseBubbleState
import com.umc.edison.presentation.baseBubble.LabelDetailMode
import com.umc.edison.presentation.model.BubbleModel
import com.umc.edison.presentation.model.LabelModel

data class LabelDetailState(
    val label: LabelModel,
    val movableLabels: List<LabelModel>,
    override val selectedBubbles: List<BubbleModel>,
    override val mode: BaseBubbleMode,
    override val isLoading: Boolean,
    override val error: Throwable?,
    override val toastMessage: String?
) : BaseBubbleState<LabelDetailMode>(selectedBubbles, mode, isLoading, error, toastMessage) {
    companion object {
        val DEFAULT = LabelDetailState(
            label = LabelModel.DEFAULT,
            movableLabels = emptyList(),
            selectedBubbles = emptyList(),
            mode = LabelDetailMode.NONE,
            isLoading = false,
            error = null,
            toastMessage = null
        )
    }

    override fun copyState(
        selectedBubbles: List<BubbleModel>,
        mode: BaseBubbleMode,
        isLoading: Boolean,
        error: Throwable?,
        toastMessage: String?
    ): BaseBubbleState<LabelDetailMode> {
        return copy(
            selectedBubbles = selectedBubbles,
            mode = mode,
            isLoading = isLoading,
            error = error,
            toastMessage = toastMessage
        )
    }
}