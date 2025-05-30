package com.umc.edison.presentation.mypage

import com.umc.edison.presentation.model.BubbleModel

data class TrashState(
    val bubbles: List<BubbleModel>,
    val selectedBubbles: List<BubbleModel>,
    val mode: BubbleRecoverMode,
) {
    companion object {
        val DEFAULT = TrashState(
            bubbles = emptyList(),
            selectedBubbles = emptyList(),
            mode = BubbleRecoverMode.NONE,
        )
    }
}

enum class BubbleRecoverMode {
    NONE, SELECT, DELETE, VIEW
}
