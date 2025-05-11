package com.umc.edison.domain.usecase.artletter

import com.umc.edison.data.DataResource
import com.umc.edison.domain.model.artLetter.ArtLetter
import com.umc.edison.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllScrappedArtLettersByCategoryUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(category: String): Flow<DataResource<List<ArtLetter>>> =
        userRepository.getScrapArtLettersByCategory(category)
}