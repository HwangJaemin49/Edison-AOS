package com.umc.edison.presentation.label

import com.umc.edison.presentation.model.LabelModel
import com.umc.edison.ui.theme.Gray300

data class LabelListState(
    val isLoading: Boolean,
    val labels: List<LabelModel>,
    val selectedLabel: LabelModel,
    val labelEditMode: LabelEditMode,
    val error: Throwable? = null,
) {
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

enum class LabelEditMode {
    NONE, ADD, EDIT, DELETE
}