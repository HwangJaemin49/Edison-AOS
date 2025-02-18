package com.umc.edison.domain.usecase.mypage

import com.umc.edison.domain.DataResource
import com.umc.edison.domain.model.ArtLetterPreview
import com.umc.edison.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetScrapArtLettersByCategoryUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(category: String): Flow<DataResource<List<ArtLetterPreview>>> =
        userRepository.getScrapArtLettersByCategory(category)
}