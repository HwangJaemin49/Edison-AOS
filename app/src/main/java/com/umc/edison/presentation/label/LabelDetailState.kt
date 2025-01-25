package com.umc.edison.presentation.label

data class LabelDetailState(
    val isLoading: Boolean,
//    val bubbles: List<BubbleModel>,
    val error: Throwable? = null,
) {
    companion object {
        val DEFAULT = LabelDetailState(
            isLoading = false,
//            bubbles = emptyList(),
        )
    }
}

enum class BubbleEditMode {
    NONE, MOVE, DELETE
}