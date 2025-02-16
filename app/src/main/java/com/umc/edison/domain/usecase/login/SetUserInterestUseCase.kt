package com.umc.edison.domain.usecase.login

import com.umc.edison.domain.DataResource
import com.umc.edison.domain.model.Interest
import com.umc.edison.domain.model.User
import com.umc.edison.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SetUserInterestUseCase@Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(interest: Interest): Flow<DataResource<Unit>> = userRepository.setUserInterest(interest)
}