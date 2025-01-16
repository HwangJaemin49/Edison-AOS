package com.umc.edison.presentation.space

import com.umc.edison.presentation.model.LabelModel
import com.umc.edison.ui.space.EditMode

data class LabelListState(
    val isLoading: Boolean,
    val labels: List<LabelModel>,
    val editMode: EditMode,
    val error: Throwable? = null,
) {
    companion object {
        val DEFAULT = LabelListState(
            isLoading = false,
            labels = emptyList(),
            editMode = EditMode.NONE,
        )
    }
}