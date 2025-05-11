package com.umc.edison.domain.usecase.artletter

import com.umc.edison.domain.DataResource
import com.umc.edison.domain.model.artLetter.ArtLetter
import com.umc.edison.domain.repository.ArtLetterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchArtLettersUseCase @Inject constructor(
    private val artLetterRepository: ArtLetterRepository
) {
    operator fun invoke(
        keyword: String,
        sortType: String = "default"
    ): Flow<DataResource<List<ArtLetter>>> =
        artLetterRepository.searchArtLetters(keyword, sortType)
}
