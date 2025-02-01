package com.umc.edison.presentation.label

import com.umc.edison.presentation.base.BaseState
import com.umc.edison.presentation.model.BubbleModel
import com.umc.edison.presentation.model.LabelModel
import com.umc.edison.ui.theme.Gray300

data class LabelDetailState(
    override val isLoading: Boolean,
    val label: LabelModel,
    val selectedBubbles: List<BubbleModel> = listOf(),
    val labelDetailMode: LabelDetailMode,
    val movableLabels: List<LabelModel> = listOf(),
    override val error: Throwable? = null,
    override val errorMessage: String? = null
) : BaseState {
    companion object {
        val DEFAULT = LabelDetailState(
            isLoading = false,
            label = LabelModel(
                id = 0,
                name = "",
                color = Gray300,
                bubbles = listOf()
            ),
            labelDetailMode = LabelDetailMode.NONE
        )
    }
}

enum class LabelDetailMode {
    NONE, VIEW, EDIT, MOVE, DELETE
}