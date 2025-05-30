package com.umc.edison.data.repository

import com.umc.edison.data.bound.flowDataResource
import com.umc.edison.data.datasources.UserRemoteDataSource
import com.umc.edison.domain.DataResource
import com.umc.edison.domain.repository.RecentSearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RecentSearchRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource
) : RecentSearchRepository {
    // READ
    override fun getAllRecentSearches(): Flow<DataResource<List<String>>> =
        flowDataResource(
            dataAction = { userRemoteDataSource.getAllRecentSearches() }
        )

    // DELETE
    override fun deleteRecentSearch(search: String): Flow<DataResource<Unit>> =
        flowDataResource(
            dataAction = { userRemoteDataSource.deleteRecentSearch(search) }
        )
}