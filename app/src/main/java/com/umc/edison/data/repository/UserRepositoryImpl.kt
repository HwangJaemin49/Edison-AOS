package com.umc.edison.data.repository

import com.umc.edison.data.bound.flowDataResource
import com.umc.edison.data.datasources.UserRemoteDataSource
import com.umc.edison.data.model.toData
import com.umc.edison.domain.DataResource
import com.umc.edison.domain.model.ArtLetter
import com.umc.edison.domain.model.ArtLetterCategory
import com.umc.edison.domain.model.IdentityKeyword
import com.umc.edison.domain.model.InterestKeyword
import com.umc.edison.domain.model.User
import com.umc.edison.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource,
) : UserRepository {
    override fun getMyIdentityKeywords(): Flow<DataResource<List<IdentityKeyword>>> = flowDataResource(
        dataAction = { userRemoteDataSource.getMyIdentityKeywords() }
    )

    override fun getMyInterestKeyword(): Flow<DataResource<InterestKeyword>>  = flowDataResource(
        dataAction = { userRemoteDataSource.getMyInterestKeyword() }
    )

    override fun getLogInState(): Flow<DataResource<Boolean>> = flowDataResource(
        dataAction = { userRemoteDataSource.getLogInState() }
    )

    override fun getMyScrapArtLetters(): Flow<DataResource<List<ArtLetterCategory>>> = flowDataResource(
        dataAction = { userRemoteDataSource.getMyScrapArtLetters() }
    )

    override fun getProfileInfo(): Flow<DataResource<User>> = flowDataResource(
        dataAction = { userRemoteDataSource.getProfileInfo() }
    )

    override fun getScrapArtLettersByCategory(categoryId: Int): Flow<DataResource<List<ArtLetter>>> = flowDataResource(
        dataAction = { userRemoteDataSource.getScrapArtLettersByCategory(categoryId) }
    )

    override fun updateProfileInfo(user: User): Flow<DataResource<Unit>> = flowDataResource(
        dataAction = { userRemoteDataSource.updateProfileInfo(user.toData()) }
    )
}