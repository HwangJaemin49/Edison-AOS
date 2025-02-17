package com.umc.edison.domain.usecase.artletter

import com.umc.edison.domain.DataResource
import com.umc.edison.domain.repository.ArtLetterRepository
import com.umc.edison.remote.model.artletter.ScrapArtLettersResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ScrapArtLettersUseCase @Inject constructor(
    private val artletterRepository: ArtLetterRepository
) {
    operator fun invoke(artLetterId: Int): Flow<DataResource<ScrapArtLettersResult>> {
        return artletterRepository.toggleScrap(artLetterId)
    }
}