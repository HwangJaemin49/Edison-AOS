package com.umc.edison.domain.usecase.login

import com.umc.edison.domain.DataResource
import com.umc.edison.domain.model.User
import com.umc.edison.domain.repository.UserRepository
import com.umc.edison.remote.model.login.TokenResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GoogleLoginUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(idToken: String): Flow<DataResource<User>> = userRepository.googleLogin(idToken)
}
