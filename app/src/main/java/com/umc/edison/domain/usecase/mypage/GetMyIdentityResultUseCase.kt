package com.umc.edison.domain.usecase.mypage

import com.umc.edison.domain.DataResource
import com.umc.edison.domain.model.Identity
import com.umc.edison.domain.model.IdentityCategory
import com.umc.edison.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMyIdentityResultUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    operator fun invoke(identityCategory: IdentityCategory): Flow<DataResource<Identity>> =
        userRepository.getMyIdentityResult(identityCategory)
}