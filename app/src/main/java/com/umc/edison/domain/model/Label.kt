package com.umc.edison.domain.model

import androidx.compose.ui.graphics.Color

data class Label(
    val id: Int = 0,
    val name: String,
    val color: Color,
    val bubbles: List<Bubble>
)
