package com.umc.edison.presentation.baseBubble

sealed class BaseBubbleMode(val modeName: String) {
    data object NONE : BaseBubbleMode("none")
    data object VIEW : BaseBubbleMode("view")
    data object EDIT : BaseBubbleMode("edit")
    data object DELETE : BaseBubbleMode("delete")
}