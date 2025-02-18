package com.umc.edison.data.repository

import com.umc.edison.data.bound.flowDataResource
import com.umc.edison.data.datasources.ArtLetterRemoteDataSource
import com.umc.edison.domain.DataResource
import com.umc.edison.domain.model.ArtLetterPreview
import com.umc.edison.domain.model.ArtLetterDetail
import com.umc.edison.domain.repository.ArtLetterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ArtLetterRepositoryImpl @Inject constructor(
    private val artLetterRemoteDataSource: ArtLetterRemoteDataSource,
) : ArtLetterRepository {

    override fun getAllArtLetters(): Flow<DataResource<List<ArtLetterPreview>>> =
        flowDataResource(
            dataAction = { artLetterRemoteDataSource.getAllArtLetters() }
        )

    override fun getArtLetterDetail(letterId: Int): Flow<DataResource<ArtLetterDetail>> =
        flowDataResource(
            dataAction = { artLetterRemoteDataSource.getArtLetterDetail(letterId) }
        )

    override fun getSortedArtLetters(sortBy: String): Flow<DataResource<List<ArtLetterPreview>>> =
        flowDataResource(
            dataAction = { artLetterRemoteDataSource.getSortedArtLetters(sortBy) }
        )

    override fun getRandomArtLetters(): Flow<DataResource<List<ArtLetterPreview>>> =
        flowDataResource(
            dataAction = { artLetterRemoteDataSource.getRandomArtLetters() }
        )

    override fun postArtLetterScrap(id: Int): Flow<DataResource<Unit>> =
        flowDataResource(
            dataAction = { artLetterRemoteDataSource.postArtLetterScrap(id) }
        )

    override fun postArtLetterLike(id: Int): Flow<DataResource<Unit>> =
        flowDataResource(
            dataAction = { artLetterRemoteDataSource.postArtLetterLike(id) }
        )

    override fun getEditorPickArtLetters(): Flow<DataResource<List<ArtLetterPreview>>> =
        flowDataResource(
            dataAction = { artLetterRemoteDataSource.postEditorPickArtLetter() }
        )
}
