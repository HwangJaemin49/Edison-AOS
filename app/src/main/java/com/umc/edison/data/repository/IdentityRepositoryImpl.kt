package com.umc.edison.data.repository

import com.umc.edison.data.bound.flowDataResource
import com.umc.edison.data.datasources.UserRemoteDataSource
import com.umc.edison.data.model.identity.IdentityCategoryMapper
import com.umc.edison.data.model.identity.toData
import com.umc.edison.domain.DataResource
import com.umc.edison.domain.model.identity.Identity
import com.umc.edison.domain.model.identity.IdentityCategory
import com.umc.edison.domain.repository.IdentityRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IdentityRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource
) : IdentityRepository {
    // CREATE
    override fun addIdentity(identity: Identity): Flow<DataResource<Unit>> =
        flowDataResource(
            dataAction = { userRemoteDataSource.addIdentity(identity.toData()) }
        )

    // READ
    override fun getAllMyIdentityResults(): Flow<DataResource<List<Identity>>> =
        flowDataResource(
            dataAction = { userRemoteDataSource.getAllMyIdentityResults() }
        )

    override fun getIdentityByCategory(category: IdentityCategory): Flow<DataResource<Identity>> =
        flowDataResource(
            dataAction = {
                val categoryNumber = IdentityCategoryMapper.entries.first {
                    it.category == category
                }.categoryNumber

                userRemoteDataSource.getIdentityByCategory(categoryNumber)
            }
        )

    override fun getMyIdentityResultByCategory(category: IdentityCategory): Flow<DataResource<Identity>> =
        flowDataResource(
            dataAction = {
                val categoryNumber = IdentityCategoryMapper.entries.first {
                    it.category == category
                }.categoryNumber

                userRemoteDataSource.getMyIdentityResult(categoryNumber)
            }
        )

    // UPDATE
    override fun updateMyIdentity(identity: Identity): Flow<DataResource<Unit>> =
        flowDataResource(
            dataAction = { userRemoteDataSource.updateIdentity(identity.toData()) }
        )

}