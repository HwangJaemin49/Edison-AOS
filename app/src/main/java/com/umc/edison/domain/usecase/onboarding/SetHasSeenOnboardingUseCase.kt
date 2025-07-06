package com.umc.edison.domain.usecase.onboarding

import com.umc.edison.domain.DataResource
import com.umc.edison.domain.repository.OnboardingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SetHasSeenOnboardingUseCase @Inject constructor(
    private val onboardingRepository: OnboardingRepository
) {
    operator fun invoke(screen: String): Flow<DataResource<Unit>> =
        onboardingRepository.setSeen(screen)
}