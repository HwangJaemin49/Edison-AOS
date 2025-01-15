package com.umc.edison.domain.model

import androidx.compose.ui.graphics.Color

data class Label(
    val id: Int? = null,
    val name: String,
    val color: Color,
    val bubbleCnt: Int
)
