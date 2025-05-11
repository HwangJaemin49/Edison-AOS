package com.umc.edison.domain.usecase.identity

import com.umc.edison.domain.DataResource
import com.umc.edison.domain.model.identity.Identity
import com.umc.edison.domain.model.identity.IdentityCategory
import com.umc.edison.domain.repository.IdentityRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMyIdentityResultUseCase @Inject constructor(
    private val identityRepository: IdentityRepository,
) {
    operator fun invoke(category: IdentityCategory): Flow<DataResource<Identity>> =
        identityRepository.getMyIdentityResultByCategory(category)
}