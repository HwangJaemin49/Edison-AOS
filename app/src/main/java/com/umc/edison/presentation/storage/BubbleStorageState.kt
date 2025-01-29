package com.umc.edison.presentation.storage

import com.umc.edison.presentation.model.BubbleModel

data class BubbleStorageState(
    val bubbles: List<BubbleModel> = emptyList(),
    val isLoading: Boolean,
    val error: Throwable?,
) {
    companion object {
        val DEFAULT = BubbleStorageState(
            isLoading = false,
//            bubbles = emptyList(),
            error = null
        )
    }
}
