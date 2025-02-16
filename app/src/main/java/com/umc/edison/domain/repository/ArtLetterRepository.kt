package com.umc.edison.domain.repository


import com.umc.edison.domain.DataResource
import com.umc.edison.domain.model.ArtLetter
import com.umc.edison.domain.model.ArtLetterDetail
import com.umc.edison.remote.model.artletter.ScrapArtLettersResult
import kotlinx.coroutines.flow.Flow

interface ArtLetterRepository {
    fun getAllArtLetters(): Flow<DataResource<List<ArtLetter>>>

    fun getArtLetterDetail(latterId: Int): Flow<DataResource<ArtLetterDetail>>

    fun getSortedArtLetters(sortBy: String): Flow<DataResource<List<ArtLetter>>>

    fun toggleScrap(artLetterId: Int): Flow<DataResource<ScrapArtLettersResult>>

    fun postEditorPick(artletterIds: List<Int>): Flow<DataResource<List<ArtLetterDetail>>>
}
