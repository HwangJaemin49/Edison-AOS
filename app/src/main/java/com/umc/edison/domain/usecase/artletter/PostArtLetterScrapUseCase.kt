package com.umc.edison.domain.usecase.artletter

import com.umc.edison.domain.DataResource
import com.umc.edison.domain.model.ArtLetterScrap
import com.umc.edison.domain.repository.ArtLetterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostArtLetterScrapUseCase @Inject constructor(
    private val artletterRepository: ArtLetterRepository
){
    operator fun invoke(artletterId: Int): Flow<DataResource<ArtLetterScrap>> = artletterRepository.postArtLetterScrap(artletterId)
}