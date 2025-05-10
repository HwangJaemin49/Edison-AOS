package com.umc.edison.presentation.baseBubble

sealed class BaseBubbleMode(val mode: String) {
    data object NONE : BaseBubbleMode("none")
    data object EDIT : BaseBubbleMode("edit")
    data object DELETE : BaseBubbleMode("delete")
}
