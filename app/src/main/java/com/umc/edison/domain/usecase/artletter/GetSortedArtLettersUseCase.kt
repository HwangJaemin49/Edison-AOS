package com.umc.edison.domain.usecase.artletter

import com.umc.edison.domain.DataResource
import com.umc.edison.domain.model.ArtLetter
import com.umc.edison.domain.repository.ArtLetterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSortedArtLettersUseCase @Inject constructor (
    private val artletterRepository: ArtLetterRepository
) {
    operator fun invoke(sortBy: String): Flow<DataResource<List<ArtLetter>>> = artletterRepository.getSortedArtLetters(sortBy)
}