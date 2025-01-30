package com.umc.edison.presentation.storage

import com.umc.edison.presentation.model.BubbleModel

data class BubbleStorageState(
    val bubbles: List<BubbleModel> = emptyList(),
    val selectedBubbles: List<BubbleModel> = emptyList(),
    val bubbleStorageMode: BubbleStorageMode = BubbleStorageMode.NONE,
    val isLoading: Boolean,
    val error: Throwable?,
) {
    companion object {
        val DEFAULT = BubbleStorageState(
            isLoading = false,
            error = null
        )
    }
}

enum class BubbleStorageMode {
    NONE, VIEW, EDIT, SHARE, DELETE
}
