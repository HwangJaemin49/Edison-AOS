package com.umc.edison.remote.datasources

import com.umc.edison.data.datasources.ArtLetterRemoteDataSource
import com.umc.edison.data.model.ArtLetterDetailEntity
import com.umc.edison.data.model.ArtLetterPreviewEntity
import com.umc.edison.remote.api.ArtLetterApiService
import com.umc.edison.remote.model.artletter.toData
import javax.inject.Inject

class ArtLetterRemoteDataSourceImpl @Inject constructor(
    private val artLetterApiService: ArtLetterApiService
) : ArtLetterRemoteDataSource {
    override suspend fun getAllArtLetters(): List<ArtLetterPreviewEntity> {
        val response = artLetterApiService.getAllArtLetters().data

        return response.map { it.toData() }
    }

    override suspend fun getSortedArtLetters(sortBy: String): List<ArtLetterPreviewEntity> {
        return artLetterApiService.getSortedArtLetters(sortBy).data.toData()
    }

    override suspend fun getArtLetterDetail(id: Int): ArtLetterDetailEntity {
        return artLetterApiService.getArtLetterDetail(id).data.toData()
    }

    override suspend fun postArtLetterLike(id: Int) {
        artLetterApiService.postArtLetterLike(id)
    }

    override suspend fun postArtLetterScrap(id: Int) {
        artLetterApiService.postArtLetterScrap(id)
    }

    override suspend fun postEditorPickArtLetter(): List<ArtLetterPreviewEntity> {
        return artLetterApiService.getEditorPick().data.map { it.toData() }
    }
}