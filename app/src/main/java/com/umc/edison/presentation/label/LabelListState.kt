package com.umc.edison.presentation.label

data class LabelListState(
    val isLoading: Boolean,
    val labels: List<LabelListModel>,
    val labelEditMode: LabelEditMode,
    val error: Throwable? = null,
) {
    companion object {
        val DEFAULT = LabelListState(
            isLoading = false,
            labels = emptyList(),
            labelEditMode = LabelEditMode.NONE,
        )
    }
}

enum class LabelEditMode {
    NONE, ADD, EDIT, DELETE
}