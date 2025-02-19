package com.umc.edison.data.datasources

import com.umc.edison.data.model.ArtLetterDetailEntity
import com.umc.edison.data.model.ArtLetterPreviewEntity

interface ArtLetterRemoteDataSource {
    suspend fun getAllArtLetters(): List<ArtLetterPreviewEntity>

    suspend fun getArtLetterDetail(id: Int): ArtLetterDetailEntity

    suspend fun getSortedArtLetters(sortBy: String): List<ArtLetterPreviewEntity>

    suspend fun getRandomArtLetters(): List<ArtLetterPreviewEntity>

    suspend fun postArtLetterScrap(id: Int)

    suspend fun postArtLetterLike(id: Int)

    suspend fun postEditorPickArtLetter(): List<ArtLetterPreviewEntity>
}