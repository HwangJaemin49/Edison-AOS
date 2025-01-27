package com.umc.edison.presentation.space

data class BubbleSpaceState(
    val isLoading: Boolean,
//    val bubbles: List<BubbleModel>,
    val error: Throwable?,
) {
    companion object {
        val DEFAULT = BubbleSpaceState(
            isLoading = false,
//            bubbles = emptyList(),
            error = null
        )
    }
}