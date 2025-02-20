package com.umc.edison.domain.usecase.artletter

import com.umc.edison.domain.DataResource
import com.umc.edison.domain.model.ArtLetterKeyWord
import com.umc.edison.domain.repository.ArtLetterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetArtLetterKeyWordUseCase @Inject constructor(
    private val artLetterRepository: ArtLetterRepository
) {
    operator fun invoke(artletterIds: List<Int>): Flow<DataResource<List<ArtLetterKeyWord>>> {
        return artLetterRepository.getArtLetterKeyWord(artletterIds)
    }
}