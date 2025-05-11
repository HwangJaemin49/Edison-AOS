package com.umc.edison.domain.usecase.user

import com.umc.edison.data.DataResource
import com.umc.edison.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(): Flow<DataResource<Unit>> = userRepository.deleteAccount()
}