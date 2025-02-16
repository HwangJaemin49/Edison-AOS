package com.umc.edison.presentation.space

import com.umc.edison.presentation.base.BaseState
import com.umc.edison.presentation.model.BubbleModel

data class BubbleSpaceState(
    val selectedBubble: BubbleModel? = null,
    override val isLoading: Boolean,
    override val error: Throwable?,
    override val toastMessage: String?

) : BaseState {
    companion object {
        val DEFAULT = BubbleSpaceState(
            isLoading = false,
            error = null,
            toastMessage = null
        )
    }
}
