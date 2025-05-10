package com.umc.edison.presentation.space

import com.umc.edison.presentation.model.BubbleModel

data class BubbleSpaceState(
    val tabs: List<String>,
    val selectedTabIndex: Int,
    val mode: BubbleSpaceMode,
    val selectedBubble: BubbleModel?,
    val query: String,
    val searchResults: List<BubbleModel>,
    val isLoggedIn: Boolean,
) {
    companion object {
        val DEFAULT = BubbleSpaceState(
            tabs = listOf("스페이스", "라벨"),
            selectedTabIndex = 0,
            mode = BubbleSpaceMode.DEFAULT,
            selectedBubble = null,
            query = "",
            searchResults = emptyList(),
            isLoggedIn = false,
        )
    }
}

enum class BubbleSpaceMode {
    DEFAULT,
    SEARCH,
}
