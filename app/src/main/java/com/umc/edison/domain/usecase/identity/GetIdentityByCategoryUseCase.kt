package com.umc.edison.domain.usecase.identity

import com.umc.edison.data.DataResource
import com.umc.edison.domain.model.identity.Identity
import com.umc.edison.domain.model.identity.IdentityCategory
import com.umc.edison.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetIdentityByCategoryUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(category: IdentityCategory): Flow<DataResource<Identity>> =
        userRepository.getIdentityKeywordsByCategory(category)
}