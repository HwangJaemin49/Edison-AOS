package com.umc.edison.data.datasources

import com.umc.edison.data.model.ArtLetterDetailEntity
import com.umc.edison.data.model.ArtletterEntity
import com.umc.edison.remote.model.artletter.ScrapArtLettersResult

interface ArtLetterRemoteDataSource {
    suspend fun getAllArtLetters(): List<ArtletterEntity>

    suspend fun getSortedArtLetters(sortBy: String): List<ArtletterEntity>

    suspend fun toggleScrap(artLetterId: Int): ScrapArtLettersResult

    suspend fun postEditorPick(artletterIds: List<Int>): List<ArtLetterDetailEntity>
}