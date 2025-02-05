package com.umc.edison.presentation.label

import com.umc.edison.presentation.base.BaseState
import com.umc.edison.presentation.model.LabelModel

data class LabelListState(
    override val isLoading: Boolean,
    val labels: List<LabelModel>,
    val selectedLabel: LabelModel,
    val labelEditMode: LabelEditMode,
    override val error: Throwable? = null,
    override val toastMessage: String? = null,
) : BaseState {
    companion object {
        val DEFAULT = LabelListState(
            isLoading = false,
            labels = emptyList(),
            selectedLabel = LabelModel.DEFAULT,
            labelEditMode = LabelEditMode.NONE,
        )
    }
}
