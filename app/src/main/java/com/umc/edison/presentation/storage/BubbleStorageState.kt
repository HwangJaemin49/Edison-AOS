package com.umc.edison.presentation.storage

import com.umc.edison.presentation.baseBubble.BaseBubbleMode
import com.umc.edison.presentation.baseBubble.BaseBubbleState
import com.umc.edison.presentation.baseBubble.BubbleStorageMode
import com.umc.edison.presentation.model.BubbleModel

data class BubbleStorageState(
    val bubbles: List<BubbleModel>,
    override val selectedBubbles: List<BubbleModel>,
    override val mode: BaseBubbleMode,
) : BaseBubbleState<BubbleStorageMode>(selectedBubbles, mode) {
    companion object {
        val DEFAULT = BubbleStorageState(
            bubbles = emptyList(),
            selectedBubbles = emptyList(),
            mode = BubbleStorageMode.NONE,
        )
    }

    override fun copyState(
        selectedBubbles: List<BubbleModel>,
        mode: BaseBubbleMode,
    ): BaseBubbleState<BubbleStorageMode> {
        return copy(
            selectedBubbles = selectedBubbles,
            mode = mode,
        )
    }
}
