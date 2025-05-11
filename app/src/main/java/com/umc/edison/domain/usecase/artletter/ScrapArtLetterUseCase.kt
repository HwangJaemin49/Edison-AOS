package com.umc.edison.domain.usecase.artletter

import com.umc.edison.data.DataResource
import com.umc.edison.domain.repository.ArtLetterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ScrapArtLetterUseCase @Inject constructor(
    private val artLetterRepository: ArtLetterRepository
) {
    operator fun invoke(id: Int): Flow<DataResource<Unit>> =
        artLetterRepository.postArtLetterScrap(id)
}
