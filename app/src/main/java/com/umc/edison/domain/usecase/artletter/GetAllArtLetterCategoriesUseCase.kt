package com.umc.edison.domain.usecase.artletter

import com.umc.edison.data.DataResource
import com.umc.edison.domain.repository.ArtLetterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllArtLetterCategoriesUseCase @Inject constructor(
    private val artLetterRepository: ArtLetterRepository
) {
    operator fun invoke(): Flow<DataResource<List<String>>> =
        artLetterRepository.getArtLetterCategory()
}
