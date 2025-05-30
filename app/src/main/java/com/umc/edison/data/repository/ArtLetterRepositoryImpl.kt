package com.umc.edison.data.repository

import com.umc.edison.data.bound.flowDataResource
import com.umc.edison.data.datasources.ArtLetterRemoteDataSource
import com.umc.edison.domain.DataResource
import com.umc.edison.domain.model.artLetter.ArtLetter
import com.umc.edison.domain.model.artLetter.ArtLetterKeyWord
import com.umc.edison.domain.repository.ArtLetterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ArtLetterRepositoryImpl @Inject constructor(
    private val artLetterRemoteDataSource: ArtLetterRemoteDataSource,
) : ArtLetterRepository {
    // READ
    override fun getAllArtLetterCategories(): Flow<DataResource<List<String>>> =
        flowDataResource(
            dataAction = { artLetterRemoteDataSource.getAllArtLetterCategories() }
        )

    override fun getAllArtLetters(): Flow<DataResource<List<ArtLetter>>> =
        flowDataResource(
            dataAction = { artLetterRemoteDataSource.getAllArtLetters() }
        )

    override fun getAllEditorPickArtLetters(): Flow<DataResource<List<ArtLetter>>> =
        flowDataResource(
            dataAction = { artLetterRemoteDataSource.getAllEditorPickArtLetters() }
        )

    override fun getAllRandomArtLetters(): Flow<DataResource<List<ArtLetter>>> =
        flowDataResource(
            dataAction = { artLetterRemoteDataSource.getAllRandomArtLetters() }
        )

    override fun getAllRecommendArtLetterKeyWords(): Flow<DataResource<List<ArtLetterKeyWord>>> =
        flowDataResource(
            dataAction = { artLetterRemoteDataSource.getAllRecommendArtLetterKeyWords() }
        )

    override fun getAllScrappedArtLetters(): Flow<DataResource<List<ArtLetter>>> =
        flowDataResource(
            dataAction = { artLetterRemoteDataSource.getAllScrappedArtLetters() }
        )

    override fun getArtLetter(letterId: Int): Flow<DataResource<ArtLetter>> =
        flowDataResource(
            dataAction = { artLetterRemoteDataSource.getArtLetter(letterId) }
        )

    override fun getScrappedArtLettersByCategory(category: String): Flow<DataResource<List<ArtLetter>>> =
        flowDataResource(
            dataAction = { artLetterRemoteDataSource.getScrappedArtLettersByCategory(category) }
        )

    override fun getSortedArtLetters(sortBy: String): Flow<DataResource<List<ArtLetter>>> =
        flowDataResource(
            dataAction = { artLetterRemoteDataSource.getSortedArtLetters(sortBy) }
        )

    override fun searchArtLetters(
        keyword: String,
        sortType: String
    ): Flow<DataResource<List<ArtLetter>>> =
        flowDataResource(
            dataAction = { artLetterRemoteDataSource.getSearchArtLetterResults(keyword, sortType) }
        )

    // UPDATE
    override fun likeArtLetter(id: Int): Flow<DataResource<Unit>> =
        flowDataResource(
            dataAction = { artLetterRemoteDataSource.postArtLetterLike(id) }
        )

    override fun scrapArtLetter(id: Int): Flow<DataResource<Unit>> =
        flowDataResource(
            dataAction = { artLetterRemoteDataSource.postArtLetterScrap(id) }
        )
}
