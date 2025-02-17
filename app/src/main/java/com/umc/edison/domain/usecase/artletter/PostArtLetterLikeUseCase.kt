package com.umc.edison.domain.usecase.artletter

import com.umc.edison.domain.DataResource
import com.umc.edison.domain.model.ArtLetterMark
import com.umc.edison.domain.repository.ArtLetterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostArtLetterLikeUseCase @Inject constructor(
    private val artletterRepository: ArtLetterRepository
){
    operator fun invoke(artletterId: Int): Flow<DataResource<ArtLetterMark>> = artletterRepository.postArtLetterLike(artletterId)
}