package com.umc.edison.domain.usecase.mypage

import com.umc.edison.domain.DataResource
import com.umc.edison.domain.model.Identity
import com.umc.edison.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateIdentityUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(identity: Identity): Flow<DataResource<Unit>> = userRepository.updateIdentity(identity)
}