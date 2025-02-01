package com.umc.edison.data.datasources

import com.umc.edison.data.model.ArtLetterCategoryEntity
import com.umc.edison.data.model.IdentityEntity
import com.umc.edison.data.model.InterestEntity
import com.umc.edison.data.model.UserEntity

interface UserRemoteDataSource {
    suspend fun getAllMyIdentityResults(): List<IdentityEntity>
    suspend fun getMyInterestKeyword(): InterestEntity
    suspend fun getLogInState(): Boolean
    suspend fun getMyScrapArtLetters(): List<ArtLetterCategoryEntity>
    suspend fun getProfileInfo(): UserEntity
    suspend fun getScrapArtLettersByCategory(categoryId: Int): List<ArtLetterCategoryEntity>

    suspend fun updateProfileInfo(user: UserEntity)

    suspend fun getMyIdentityResult(categoryNumber: String): IdentityEntity
}