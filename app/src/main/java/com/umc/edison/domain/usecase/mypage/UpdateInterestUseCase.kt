package com.umc.edison.domain.usecase.mypage

import com.umc.edison.domain.DataResource
import com.umc.edison.domain.model.Interest
import com.umc.edison.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateInterestUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(interest: Interest): Flow<DataResource<Unit>> =
        userRepository.updateInterest(interest)
}