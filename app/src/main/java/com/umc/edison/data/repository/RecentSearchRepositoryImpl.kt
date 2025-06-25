package com.umc.edison.data.repository

import com.umc.edison.data.bound.FlowBoundResourceFactory
import com.umc.edison.data.datasources.UserRemoteDataSource
import com.umc.edison.domain.DataResource
import com.umc.edison.domain.repository.RecentSearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RecentSearchRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val resourceFactory: FlowBoundResourceFactory
) : RecentSearchRepository {
    // READ
    override fun getAllRecentSearches(): Flow<DataResource<List<String>>> =
        resourceFactory.remote(
            dataAction = { userRemoteDataSource.getAllRecentSearches() }
        )

    // DELETE
    override fun deleteRecentSearch(search: String): Flow<DataResource<Unit>> =
        resourceFactory.remote(
            dataAction = { userRemoteDataSource.deleteRecentSearch(search) }
        )
}