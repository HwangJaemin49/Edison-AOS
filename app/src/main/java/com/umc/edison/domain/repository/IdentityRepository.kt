package com.umc.edison.domain.repository

import com.umc.edison.domain.DataResource
import com.umc.edison.domain.model.identity.Identity
import com.umc.edison.domain.model.identity.IdentityCategory
import kotlinx.coroutines.flow.Flow

interface IdentityRepository {
    // CREATE
    fun addIdentity(identity: Identity): Flow<DataResource<Unit>>

    // READ
    fun getAllMyIdentityResults(): Flow<DataResource<List<Identity>>>
    fun getIdentityByCategory(category: IdentityCategory): Flow<DataResource<Identity>>
    fun getMyIdentityResultByCategory(category: IdentityCategory): Flow<DataResource<Identity>>

    // UPDATE
    fun updateMyIdentity(identity: Identity): Flow<DataResource<Unit>>
}