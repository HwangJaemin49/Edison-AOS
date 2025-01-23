package com.umc.edison.presentation.label

import com.umc.edison.presentation.model.LabelModel
import com.umc.edison.ui.label.EditMode

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