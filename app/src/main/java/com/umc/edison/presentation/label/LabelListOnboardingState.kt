package com.umc.edison.presentation.label

import com.umc.edison.presentation.onboarding.OnboardingPositionState

data class LabelListOnboardingState(
    val show: Boolean,
    val labelBound: OnboardingPositionState,
) {
    companion object {
        val DEFAULT = LabelListOnboardingState(
            show = false,
            labelBound = OnboardingPositionState.DEFAULT,
        )
    }
}
