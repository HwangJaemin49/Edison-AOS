package com.umc.edison.presentation.space

import com.umc.edison.presentation.base.BaseState
import com.umc.edison.presentation.model.BubbleModel

data class BubbleSpaceState(
    val tabs: List<String> = listOf("스페이스", "라벨"),
    val selectedTabIndex: Int = 0,
    val mode: BubbleSpaceMode = BubbleSpaceMode.DEFAULT,
    val selectedBubble: BubbleModel? = null,
    val query : String = "",
    val searchResults: List<BubbleModel> = emptyList(),
    val isLoggedIn: Boolean = false,
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

enum class BubbleSpaceMode {
    DEFAULT,
    SEARCH,
}
