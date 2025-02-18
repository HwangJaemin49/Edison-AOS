package com.umc.edison.data.repository

import com.umc.edison.data.bound.flowDataResource
import com.umc.edison.data.datasources.ArtLetterRemoteDataSource
import com.umc.edison.domain.DataResource
import com.umc.edison.domain.model.ArtLetter
import com.umc.edison.domain.model.ArtLetterDetail
import com.umc.edison.domain.model.ArtLetterMark
import com.umc.edison.domain.model.ArtLetterScrap
import com.umc.edison.domain.model.EditorPickArtLetter
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

    override fun getArtLetterDetail(letterId: Int): Flow<DataResource<ArtLetterDetail>> =
        flowDataResource(
            dataAction = { artletterRemoteDataSource.getArtLetterDetail(letterId)}
        )

    override fun getSortedArtLetters(sortBy: String): Flow<DataResource<List<ArtLetter>>> =
        flowDataResource(
            dataAction = { artletterRemoteDataSource.getSortedArtLetters(sortBy) }
        )

    override fun postArtLetterScrap(artletterId: Int): Flow<DataResource<ArtLetterScrap>> =
        flowDataResource(
            dataAction = { artletterRemoteDataSource.postArtLetterScrap(artletterId) }
        )

    override fun postArtLetterLike(artletterId: Int): Flow<DataResource<ArtLetterMark>> =
        flowDataResource(
            dataAction = {artletterRemoteDataSource.postArtLetterLike(artletterId)}
        )

    override fun postEditorPickArtLetter(artletterIds: List<Int>): Flow<DataResource<List<EditorPickArtLetter>>> =
        flowDataResource(
            dataAction = { artletterRemoteDataSource.postEditorPickArtLetter(artletterIds) }
        )

}
