package com.umc.edison.data.datasources

import com.umc.edison.data.model.ArtLetterDetailEntity
import com.umc.edison.data.model.ArtLetterMarkEntity
import com.umc.edison.data.model.ArtLetterScrapEntity
import com.umc.edison.data.model.ArtletterEntity
import com.umc.edison.data.model.EditorPickArtLetterEntity

interface ArtLetterRemoteDataSource {
    suspend fun getAllArtLetters(): List<ArtletterEntity>

    suspend fun getArtLetterDetail(letterId: Int): ArtLetterDetailEntity

    suspend fun getSortedArtLetters(sortBy: String): List<ArtletterEntity>

    suspend fun postArtLetterScrap(artletterId: Int): ArtLetterScrapEntity

    suspend fun postArtLetterLike(artletterId: Int): ArtLetterMarkEntity

    suspend fun postEditorPickArtLetter(artletterIds: List<Int>): List<EditorPickArtLetterEntity>
}