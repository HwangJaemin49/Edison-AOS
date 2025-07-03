package com.umc.edison.presentation.storage

import com.umc.edison.presentation.onboarding.OnboardingPositionState

data class BubbleStorageOnboardingState(
    val edisonOnboardingShow: Boolean,
    val show: Boolean,
    val bubbleBound: OnboardingPositionState
) {
    companion object {
        val DEFAULT = BubbleStorageOnboardingState(
            edisonOnboardingShow = false,
            show = false,
            bubbleBound = OnboardingPositionState.DEFAULT
        )
    }
}
