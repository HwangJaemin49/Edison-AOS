package com.umc.edison.presentation.edison

import com.umc.edison.presentation.base.BaseState
import com.umc.edison.presentation.model.BubbleModel

data class  MyEdisonState(
    override val isLoading: Boolean,
    override val error: Throwable? = null,
    override val toastMessage: String? = null,
    val bubble: BubbleModel,
    val bubbles: List<BubbleModel>,
    val query : String = "",
    val searchResults: List<BubbleModel> = emptyList(),
): BaseState {
    companion object {
        val DEFAULT =  MyEdisonState(
            isLoading = false,
            bubble = BubbleModel(),
            bubbles = emptyList(),
        )
    }
}