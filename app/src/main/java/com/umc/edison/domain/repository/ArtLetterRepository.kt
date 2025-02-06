package com.umc.edison.domain.repository

import com.umc.edison.domain.DataResource
import com.umc.edison.domain.model.ArtLetter
import kotlinx.coroutines.flow.Flow

interface ArtLetterRepository {
    fun getAllArtLetters(): Flow<DataResource<List<ArtLetter>>>
}