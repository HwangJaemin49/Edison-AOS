package com.umc.edison.presentation.mypage

import com.umc.edison.presentation.base.BaseState
import com.umc.edison.presentation.model.BubbleModel

data class TrashState(
    override val isLoading: Boolean,
    val bubbles: List<BubbleModel>,
    val selectedBubbles: List<BubbleModel>,
    val mode: BubbleRecoverMode,
    override val error: Throwable?,
    override val toastMessage: String?
) : BaseState {
    companion object {
        val DEFAULT = TrashState(
            isLoading = false,
            bubbles = emptyList(),
            selectedBubbles = emptyList(),
            mode = BubbleRecoverMode.NONE,
            error = null,
            toastMessage = null
        )
    }
}

enum class BubbleRecoverMode {
    NONE, SELECT, DELETE
}
