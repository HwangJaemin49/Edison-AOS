package com.umc.edison.data.datasources

import com.umc.edison.data.model.IdentityKeywordEntity
import com.umc.edison.data.model.InterestKeywordEntity

interface UserRemoteDataSource {
    suspend fun getMyIdentityKeywords(): List<IdentityKeywordEntity>
    suspend fun getMyInterestKeyword(): InterestKeywordEntity
}