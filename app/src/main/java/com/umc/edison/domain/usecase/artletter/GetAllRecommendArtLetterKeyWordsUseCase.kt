package com.umc.edison.domain.usecase.artletter

import com.umc.edison.domain.DataResource
import com.umc.edison.domain.model.artLetter.ArtLetterKeyWord
import com.umc.edison.domain.repository.ArtLetterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllRecommendArtLetterKeyWordsUseCase @Inject constructor(
    private val artLetterRepository: ArtLetterRepository
) {
    operator fun invoke(): Flow<DataResource<List<ArtLetterKeyWord>>> {
        return artLetterRepository.getAllRecommendArtLetterKeyWords()
    }
}
