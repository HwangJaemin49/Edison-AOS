package com.umc.edison.domain.repository

import com.umc.edison.domain.DataResource
import com.umc.edison.domain.model.IdentityKeyword
import com.umc.edison.domain.model.InterestKeyword
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getMyIdentityKeywords(): Flow<DataResource<List<IdentityKeyword>>>
    fun getMyInterestKeyword(): Flow<DataResource<InterestKeyword>>
}