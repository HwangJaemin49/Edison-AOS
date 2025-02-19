package com.umc.edison.remote.datasources

import com.umc.edison.data.datasources.ArtLetterRemoteDataSource
import com.umc.edison.data.model.ArtLetterDetailEntity
import com.umc.edison.data.model.ArtLetterPreviewEntity
import com.umc.edison.remote.api.ArtLetterApiService
import com.umc.edison.remote.model.artletter.GetEditorPickRequest
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

    override suspend fun getRandomArtLetters(): List<ArtLetterPreviewEntity> {
        val artLetters = artLetterApiService.getAllArtLetters().data.shuffled()
        val picked = mutableListOf<ArtLetterPreviewEntity>()

        if (artLetters.size > 3) {
            picked.add(artLetters[0].toData())
            picked.add(artLetters[1].toData())
            picked.add(artLetters[2].toData())
        } else {
            picked.addAll(artLetters.map { it.toData() })
        }

        val result = mutableListOf<ArtLetterPreviewEntity>()

        for (i in 0 until picked.size) {
            val detail = getArtLetterDetail(picked[i].artLetterId)
            result.add(
                ArtLetterPreviewEntity(
                    artLetterId = picked[i].artLetterId,
                    title = picked[i].title,
                    thumbnail = picked[i].thumbnail,
                    scraped = picked[i].scraped,
                    tags = detail.tags
                )
            )
        }

        return result
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
        val request = GetEditorPickRequest(listOf(6, 7, 8))
        return artLetterApiService.getEditorPick(request).data.map { it.toData() }
    }

    override suspend fun getSearchArtLetters(keyword: String,sortType: String): List<ArtLetterPreviewEntity> {
        val response = artLetterApiService.getSearchArtLetters(keyword, sortType).data
        return response.map { it.toData() }
    }
}