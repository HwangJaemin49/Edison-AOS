package com.umc.edison.presentation.storage

import com.umc.edison.presentation.base.BaseState
import com.umc.edison.presentation.model.BubbleModel
import com.umc.edison.presentation.model.LabelModel

data class BubbleStorageState(
    val label: LabelModel? = null,
    val bubbles: List<BubbleModel> = emptyList(),
    val selectedBubbles: List<BubbleModel> = emptyList(),
    val bubbleStorageMode: BubbleStorageMode = BubbleStorageMode.NONE,
    val movableLabels: List<LabelModel> = listOf(),
    val selectedLabel: LabelModel? = null,
    val labelId: Int? = null,
    override val isLoading: Boolean,
    override val error: Throwable? = null,
    override val toastMessage: String? = null,
) : BaseState {
    companion object {
        val DEFAULT = BubbleStorageState(
            isLoading = false,
        )
    }
}

enum class BubbleStorageMode {
    NONE,
    VIEW,
    EDIT,
    DELETE,
    SHARE,
    MOVE
}
