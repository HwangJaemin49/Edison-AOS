package com.umc.edison.data.repository

import com.umc.edison.data.bound.flowDataResource
import com.umc.edison.data.datasources.UserRemoteDataSource
import com.umc.edison.domain.DataResource
import com.umc.edison.domain.model.IdentityKeyword
import com.umc.edison.domain.model.InterestKeyword
import com.umc.edison.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource
) : UserRepository {
    override fun getMyIdentityKeywords(): Flow<DataResource<List<IdentityKeyword>>> = flowDataResource(
        dataAction = { userRemoteDataSource.getMyIdentityKeywords() }
    )

    override fun getMyInterestKeyword(): Flow<DataResource<InterestKeyword>>  = flowDataResource(
        dataAction = { userRemoteDataSource.getMyInterestKeyword() }
    )
}