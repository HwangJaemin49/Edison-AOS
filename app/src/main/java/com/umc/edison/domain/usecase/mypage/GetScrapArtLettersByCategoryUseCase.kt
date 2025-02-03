package com.umc.edison.domain.usecase.mypage

import com.umc.edison.domain.DataResource
import com.umc.edison.domain.model.ArtLetter
import com.umc.edison.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetScrapArtLettersByCategoryUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(categoryId: Int): Flow<DataResource<List<ArtLetter>>> =
        userRepository.getScrapArtLettersByCategory(categoryId)
}