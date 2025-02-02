package com.umc.edison.domain.usecase.mypage

import com.umc.edison.domain.DataResource
import com.umc.edison.domain.model.User
import com.umc.edison.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProfileInfoUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(): Flow<DataResource<User>> = userRepository.getProfileInfo()
}