package com.umc.edison.domain.usecase.mypage

import com.umc.edison.domain.DataResource
import com.umc.edison.domain.model.Identity
import com.umc.edison.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllMyIdentityResultsUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(): Flow<DataResource<List<Identity>>> = userRepository.getAllMyIdentityResults()
}