package com.umc.edison.presentation.label

import com.umc.edison.presentation.base.BaseState
import com.umc.edison.presentation.model.LabelModel
import com.umc.edison.ui.theme.Gray300

data class LabelListState(
    override val isLoading: Boolean,
    val labels: List<LabelModel>,
    val selectedLabel: LabelModel,
    val labelEditMode: LabelEditMode,
    override val error: Throwable? = null,
    override val errorMessage: String? = null,
) : BaseState {
    companion object {
        val DEFAULT = LabelListState(
            isLoading = false,
            labels = emptyList(),
            selectedLabel = LabelModel(
                id = 0,
                name = "",
                color = Gray300,
                bubbles = listOf()
            ),
            labelEditMode = LabelEditMode.NONE,
        )
    }
}
