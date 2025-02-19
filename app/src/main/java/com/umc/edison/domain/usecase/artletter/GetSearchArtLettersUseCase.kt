package com.umc.edison.domain.usecase.artletter

import com.umc.edison.domain.DataResource
import com.umc.edison.domain.model.ArtLetterPreview
import com.umc.edison.domain.repository.ArtLetterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSearchArtLettersUseCase @Inject constructor(
    private val artLetterRepository: ArtLetterRepository
) {
    operator fun invoke(keyword: String, sortType: String): Flow<DataResource<List<ArtLetterPreview>>> = artLetterRepository.getSearchArtLetters(keyword, sortType)
}