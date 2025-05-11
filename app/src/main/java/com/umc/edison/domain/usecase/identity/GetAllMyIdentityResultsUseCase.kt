package com.umc.edison.domain.usecase.identity

import com.umc.edison.data.DataResource
import com.umc.edison.domain.model.identity.Identity
import com.umc.edison.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllMyIdentityResultsUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(): Flow<DataResource<List<Identity>>> =
        userRepository.getAllMyIdentityResults()
}