package com.umc.edison.remote.datasources

import com.umc.edison.data.datasources.ArtLetterRemoteDataSource
import com.umc.edison.data.model.ArtLetterDetailEntity
import com.umc.edison.data.model.ArtletterEntity
import com.umc.edison.remote.api.ArtLetterApiService
import com.umc.edison.remote.model.artletter.ScrapArtLettersResult
import com.umc.edison.remote.model.artletter.toData
import javax.inject.Inject

class ArtLetterRemoteDataSourceImpl @Inject constructor(
    private val artLetterApiService: ArtLetterApiService
) : ArtLetterRemoteDataSource {
    override suspend fun getAllArtLetters(): List<ArtletterEntity> {
        return artLetterApiService.getAllArtLetters().data.toData()
    }

    override suspend fun getSortedArtLetters(sortBy: String): List<ArtletterEntity> {
        return artLetterApiService.getSortedArtLetters(sortBy).data.toData()
    }

    override suspend fun toggleScrap(artLetterId: Int): ScrapArtLettersResult {
        return artLetterApiService.toggleScrap(artLetterId)
    }

    override suspend fun postEditorPick(artletterIds: List<Int>): List<ArtLetterDetailEntity> {
        return artLetterApiService.postEditorPick(artletterIds).data.toData()
    }
}
