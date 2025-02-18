package com.umc.edison.domain.repository


import com.umc.edison.domain.DataResource
import com.umc.edison.domain.model.ArtLetter
import com.umc.edison.domain.model.ArtLetterDetail
import com.umc.edison.domain.model.ArtLetterMark
import com.umc.edison.domain.model.ArtLetterScrap
import com.umc.edison.domain.model.EditorPickArtLetter
import kotlinx.coroutines.flow.Flow

interface ArtLetterRepository {
    fun getAllArtLetters(): Flow<DataResource<List<ArtLetter>>>

    fun getArtLetterDetail(letterId: Int): Flow<DataResource<ArtLetterDetail>>

    fun getSortedArtLetters(sortBy: String): Flow<DataResource<List<ArtLetter>>>

    fun postArtLetterScrap(artletterId: Int): Flow<DataResource<ArtLetterScrap>>

    fun postArtLetterLike(artletterId: Int): Flow<DataResource<ArtLetterMark>>

    fun postEditorPickArtLetter(artletterIds: List<Int>): Flow<DataResource<List<EditorPickArtLetter>>>
}
