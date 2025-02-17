package com.umc.edison.domain.usecase.artletter

import com.umc.edison.domain.DataResource
import com.umc.edison.domain.model.ArtLetter
import com.umc.edison.domain.model.ArtLetterDetail
import com.umc.edison.domain.repository.ArtLetterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetArtLetterDetailUseCase @Inject constructor(
    private val artletterRepository: ArtLetterRepository
){
    operator fun invoke(latterId: Int): Flow<DataResource<ArtLetterDetail>> = artletterRepository.getArtLetterDetail(latterId)
}