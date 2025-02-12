package com.umc.edison.domain.usecase.login

import com.umc.edison.domain.DataResource
import com.umc.edison.domain.model.User
import com.umc.edison.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MakeNickNameUseCase @Inject constructor( private val userRepository: UserRepository) {
    operator fun invoke(nickname: String): Flow<DataResource<Unit>> =
        userRepository.makeNickName(nickname)
}