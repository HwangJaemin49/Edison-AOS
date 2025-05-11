package com.umc.edison.domain.usecase.artletter

import com.umc.edison.data.DataResource
import com.umc.edison.domain.model.artLetter.ArtLetter
import com.umc.edison.domain.repository.ArtLetterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchAllArtLettersUseCase @Inject constructor(
    private val artLetterRepository: ArtLetterRepository
) {
    operator fun invoke(
        keyword: String,
        sortType: String = "default"
    ): Flow<DataResource<List<ArtLetter>>> =
        artLetterRepository.getSearchArtLetters(keyword, sortType)
}
