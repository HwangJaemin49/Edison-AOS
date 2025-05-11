package com.umc.edison.domain.usecase.user

import com.umc.edison.data.DataResource
import com.umc.edison.domain.model.user.User
import com.umc.edison.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GoogleLoginUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(idToken: String): Flow<DataResource<User>> =
        userRepository.googleLogin(idToken)
}
