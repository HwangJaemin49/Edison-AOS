package com.umc.edison.remote.datasources

import com.umc.edison.data.datasources.ArtLetterRemoteDataSource
import com.umc.edison.data.model.ArtLetterDetailEntity
import com.umc.edison.data.model.ArtLetterMarkEntity
import com.umc.edison.data.model.ArtLetterScrapEntity
import com.umc.edison.data.model.ArtletterEntity
import com.umc.edison.data.model.EditorPickArtLetterEntity
import com.umc.edison.remote.api.ArtLetterApiService
import com.umc.edison.remote.model.artletter.ScrapArtLettersResult
import com.umc.edison.remote.model.artletter.toData
import javax.inject.Inject

class ArtLetterRemoteDataSourceImpl @Inject constructor(
    private val artLetterApiService: ArtLetterApiService
) : ArtLetterRemoteDataSource {
    override suspend fun getAllArtLetters(): List<ArtletterEntity> {
        val response = artLetterApiService.getAllArtLetters().data

        return response.map { it.toData() }
    }

    override suspend fun getSortedArtLetters(sortBy: String): List<ArtletterEntity> {
        return artLetterApiService.getSortedArtLetters(sortBy).data.toData()
    }

    override suspend fun getArtLetterDetail(letterId: Int): ArtLetterDetailEntity {
        return artLetterApiService.getArtLetterDetail(letterId).data.toData()
    }

    override suspend fun postArtLetterLike(artletterId: Int): ArtLetterMarkEntity {
        return artLetterApiService.postArtLetterLike(artletterId).data.toData()
    }

    override suspend fun postArtLetterScrap(artletterId: Int): ArtLetterScrapEntity {
        return artLetterApiService.postArtLetterScrap(artletterId).data.toData()
    }

    override suspend fun postEditorPickArtLetter(artletterIds: List<Int>): List<EditorPickArtLetterEntity> {
        val response = artLetterApiService.postEditorPick(artletterIds).data

        return response.map { it.toData() }
    }
}