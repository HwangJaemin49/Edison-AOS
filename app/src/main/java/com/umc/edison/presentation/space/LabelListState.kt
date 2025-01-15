package com.umc.edison.presentation.space

import com.umc.edison.presentation.model.LabelModel

data class LabelListState(
    val isLoading: Boolean,
    val labels: List<LabelModel>,
    val error: Throwable?,
) {
    companion object {
        val DEFAULT = LabelListState(
            isLoading = false,
            labels = emptyList(),
            error = null
        )
    }
}