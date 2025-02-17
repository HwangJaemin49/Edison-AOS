package com.umc.edison.presentation.baseBubble

import com.umc.edison.presentation.base.BaseState
import com.umc.edison.presentation.model.BubbleModel

abstract class BaseBubbleState<M: BaseBubbleMode> (
    open val selectedBubbles: List<BubbleModel>,
    open val mode: BaseBubbleMode,
    override val isLoading: Boolean,
    override val error: Throwable?,
    override val toastMessage: String?,
) : BaseState {
    abstract fun copyState(
        selectedBubbles: List<BubbleModel> = this.selectedBubbles,
        mode: BaseBubbleMode = this.mode,
        isLoading: Boolean = this.isLoading,
        error: Throwable? = this.error,
        toastMessage: String? = this.toastMessage,
    ): BaseBubbleState<M>
}
