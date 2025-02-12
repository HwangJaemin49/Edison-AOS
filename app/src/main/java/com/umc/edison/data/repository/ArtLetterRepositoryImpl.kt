package com.umc.edison.data.repository

import com.umc.edison.data.bound.flowDataResource
import com.umc.edison.data.datasources.ArtLetterRemoteDataSource
import com.umc.edison.domain.DataResource
import com.umc.edison.domain.model.ArtLetter
import com.umc.edison.domain.model.ArtLetterDetail
import com.umc.edison.domain.repository.ArtLetterRepository
import com.umc.edison.remote.model.artletter.ScrapArtLettersResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ArtLetterRepositoryImpl @Inject constructor(
    private val artletterRemoteDataSource: ArtLetterRemoteDataSource,
) : ArtLetterRepository {

    override fun getAllArtLetters(): Flow<DataResource<List<ArtLetter>>> =
        flowDataResource(
            dataAction = { artletterRemoteDataSource.getAllArtLetters() }
        )

    override fun getSortedArtLetters(sortBy: String): Flow<DataResource<List<ArtLetter>>> =
        flowDataResource(
            dataAction = { artletterRemoteDataSource.getSortedArtLetters(sortBy) }
        )

    override fun toggleScrap(
        artLetterId: Int
    ): Flow<DataResource<ScrapArtLettersResult>> =
        flowDataResource(
            dataAction = { artletterRemoteDataSource.toggleScrap(artLetterId) }
        )

    override fun postEditorPick(artletterIds: List<Int>): Flow<DataResource<List<ArtLetterDetail>>> =
        flowDataResource(
            dataAction = { artletterRemoteDataSource.postEditorPick(artletterIds) }
        )

}
