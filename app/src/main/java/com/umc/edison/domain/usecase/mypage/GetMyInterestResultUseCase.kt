package com.umc.edison.domain.usecase.mypage

import com.umc.edison.domain.DataResource
import com.umc.edison.domain.model.Interest
import com.umc.edison.domain.model.InterestCategory
import com.umc.edison.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMyInterestResultUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(interestCategory: InterestCategory): Flow<DataResource<Interest>> =
        userRepository.getMyInterestResult(interestCategory)
}