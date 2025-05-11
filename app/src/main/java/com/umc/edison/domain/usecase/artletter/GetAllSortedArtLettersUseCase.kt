package com.umc.edison.domain.usecase.artletter

import com.umc.edison.data.DataResource
import com.umc.edison.domain.model.artLetter.ArtLetter
import com.umc.edison.domain.repository.ArtLetterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllSortedArtLettersUseCase @Inject constructor(
    private val artLetterRepository: ArtLetterRepository
) {
    operator fun invoke(sortBy: String): Flow<DataResource<List<ArtLetter>>> =
        artLetterRepository.getSortedArtLetters(sortBy)
}
