package com.umc.edison.domain.repository


import com.umc.edison.domain.DataResource
import com.umc.edison.domain.model.ArtLetterPreview
import com.umc.edison.domain.model.ArtLetterDetail
import com.umc.edison.domain.model.ArtLetterKeyWord
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

    fun getArtLetterKeyWord(): Flow<DataResource<List<ArtLetterKeyWord>>>

    fun getArtLetterCategory(): Flow<DataResource<List<String>>>

    fun removeRecentSearch(keyword: String): Flow<DataResource<Unit>>

    fun getRecentSearches(): Flow<DataResource<List<String>>>
}
