package com.umc.edison.domain.usecase.artletter

import com.umc.edison.domain.DataResource
import com.umc.edison.domain.model.EditorPickArtLetter
import com.umc.edison.domain.repository.ArtLetterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostEditorPickUseCase @Inject constructor(
    private val artletterRepository: ArtLetterRepository
) {
    operator fun invoke(artletterIds: List<Int>): Flow<DataResource<List<EditorPickArtLetter>>> {
        return artletterRepository.postEditorPickArtLetter(artletterIds)
    }

}