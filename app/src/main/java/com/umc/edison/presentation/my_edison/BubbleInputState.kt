package com.umc.edison.presentation.my_edison

data class BubbleInputState(
    val isLoading: Boolean = false,
//    val bubble: BubbleModel,
    val error: Throwable? = null
) {
    companion object {
        val DEFAULT = BubbleInputState(
            isLoading = false,
//            bubble = BubbleModel(),
            error = null
        )
    }
}