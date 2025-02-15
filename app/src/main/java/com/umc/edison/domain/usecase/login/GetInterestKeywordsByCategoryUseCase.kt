package com.umc.edison.domain.usecase.login

import com.umc.edison.domain.DataResource
import com.umc.edison.domain.model.Interest
import com.umc.edison.domain.model.InterestCategory
import com.umc.edison.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetInterestKeywordsByCategoryUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(interestCategory: InterestCategory): Flow<DataResource<Interest>> = userRepository.getInterestKeywordsByCategory(interestCategory)
}