package com.umc.edison.data.datasources

import com.umc.edison.data.model.ArtletterEntity

interface ArtLetterRemoteDataSource {
    suspend fun getAllArtLetters(): List<ArtletterEntity>
}