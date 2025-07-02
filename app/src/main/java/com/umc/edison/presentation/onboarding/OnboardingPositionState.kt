package com.umc.edison.presentation.onboarding

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntSize

data class OnboardingPositionState(
    val offset: Offset,
    val size: IntSize
) {
    companion object {
        val DEFAULT = OnboardingPositionState(
            offset = Offset.Zero,
            size = IntSize.Zero
        )
    }
}
