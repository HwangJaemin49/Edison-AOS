package com.umc.edison.domain.usecase.user

import com.umc.edison.domain.DataResource
import com.umc.edison.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLogInStateUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(): Flow<DataResource<Boolean>> = userRepository.getLogInState()
}