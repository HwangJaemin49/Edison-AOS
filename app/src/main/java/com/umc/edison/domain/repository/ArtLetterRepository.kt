package com.umc.edison.domain.repository


import com.umc.edison.domain.DataResource
import com.umc.edison.domain.model.ArtLetterPreview
import com.umc.edison.domain.model.ArtLetterDetail
import kotlinx.coroutines.flow.Flow

interface ArtLetterRepository {
    fun getEditorPickArtLetters(): Flow<DataResource<List<ArtLetterPreview>>>

    fun getAllArtLetters(): Flow<DataResource<List<ArtLetterPreview>>>

    fun getArtLetterDetail(letterId: Int): Flow<DataResource<ArtLetterDetail>>

    fun getSortedArtLetters(sortBy: String): Flow<DataResource<List<ArtLetterPreview>>>

    fun getRandomArtLetters(): Flow<DataResource<List<ArtLetterPreview>>>

    fun postArtLetterScrap(id: Int): Flow<DataResource<Unit>>

    fun postArtLetterLike(id: Int): Flow<DataResource<Unit>>

    fun getSearchArtLetters(keyword: String, sortType: String): Flow<DataResource<List<ArtLetterPreview>>>

    fun removeRecentSearch(id: Int): Flow<DataResource<Unit>>
}
