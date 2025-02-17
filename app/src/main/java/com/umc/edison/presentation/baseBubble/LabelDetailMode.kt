package com.umc.edison.presentation.baseBubble

sealed class LabelDetailMode(modeName: String) : BaseBubbleMode(modeName) {
    data object NONE : BubbleStorageMode("none")
    data object VIEW : BubbleStorageMode("view")
    data object EDIT : BubbleStorageMode("edit")
    data object DELETE : BubbleStorageMode("delete")
    data object MOVE : BubbleStorageMode("move")
}