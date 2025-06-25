package com.umc.edison.data.repository

import com.umc.edison.data.bound.FlowBoundResourceFactory
import com.umc.edison.data.datasources.UserRemoteDataSource
import com.umc.edison.data.model.identity.toData
import com.umc.edison.domain.DataResource
import com.umc.edison.domain.model.identity.Identity
import com.umc.edison.domain.model.identity.IdentityCategory
import com.umc.edison.domain.repository.IdentityRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IdentityRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val resourceFactory: FlowBoundResourceFactory
) : IdentityRepository {
    // CREATE
    override fun addUserIdentity(identity: Identity): Flow<DataResource<Unit>> =
        resourceFactory.remote(
            dataAction = { userRemoteDataSource.addIdentity(identity.toData()) }
        )

    // READ
    override fun getAllMyIdentityResults(): Flow<DataResource<List<Identity>>> =
        resourceFactory.remote(
            dataAction = { userRemoteDataSource.getAllMyIdentityResults() }
        )

    override fun getIdentityByCategory(category: IdentityCategory): Flow<DataResource<Identity>> =
        resourceFactory.remote(
            dataAction = {
                userRemoteDataSource.getIdentityByCategory(category.toData())
            }
        )

    override fun getMyIdentityResultByCategory(category: IdentityCategory): Flow<DataResource<Identity>> =
        resourceFactory.remote(
            dataAction = {
                val allResults = userRemoteDataSource.getAllMyIdentityResults()
                allResults.firstOrNull { it.category.toDomain() == category }
                    ?: throw IllegalArgumentException("Identity not found for category: $category")

            }
        )

    // UPDATE
    override fun updateMyIdentityResult(identity: Identity): Flow<DataResource<Unit>> =
        resourceFactory.remote(
            dataAction = { userRemoteDataSource.updateIdentity(identity.toData()) }
        )

}