package com.umc.edison.presentation.baseBubble

sealed class BubbleStorageMode(mode: String) : BaseBubbleMode(mode) {
    data object NONE : BubbleStorageMode("none")
    data object VIEW : BubbleStorageMode("view")
    data object EDIT : BubbleStorageMode("edit")
    data object DELETE : BubbleStorageMode("delete")
    data object SHARE : BubbleStorageMode("share")
}
