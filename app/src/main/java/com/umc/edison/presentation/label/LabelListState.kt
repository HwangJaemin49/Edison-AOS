package com.umc.edison.presentation.label

import com.umc.edison.ui.theme.Gray300

data class LabelListState(
    val isLoading: Boolean,
    val labels: List<LabelListModel>,
    val selectedLabel: LabelListModel,
    val labelEditMode: LabelEditMode,
    val error: Throwable? = null,
) {
    companion object {
        val DEFAULT = LabelListState(
            isLoading = false,
            labels = emptyList(),
            selectedLabel = LabelListModel(
                id = 0,
                name = "",
                color = Gray300,
                bubbleCnt = 0,
            ),
            labelEditMode = LabelEditMode.NONE,
        )
    }
}

enum class LabelEditMode {
    NONE, ADD, EDIT, DELETE
}