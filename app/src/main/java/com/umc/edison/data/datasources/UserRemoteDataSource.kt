package com.umc.edison.data.datasources

import com.umc.edison.data.model.ArtLetterCategoryEntity
import com.umc.edison.data.model.IdentityKeywordEntity
import com.umc.edison.data.model.InterestKeywordEntity
import com.umc.edison.data.model.UserEntity

interface UserRemoteDataSource {
    suspend fun getMyIdentityKeywords(): List<IdentityKeywordEntity>
    suspend fun getMyInterestKeyword(): InterestKeywordEntity
    suspend fun getLogInState(): Boolean
    suspend fun getMyScrapArtLetters(): List<ArtLetterCategoryEntity>
    suspend fun getProfileInfo(): UserEntity
    suspend fun getScrapArtLettersByCategory(categoryId: Int): List<ArtLetterCategoryEntity>

    suspend fun updateProfileInfo(user: UserEntity)
}