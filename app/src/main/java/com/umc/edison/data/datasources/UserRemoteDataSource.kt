package com.umc.edison.data.datasources

import com.umc.edison.data.model.ArtLetterCategoryEntity
import com.umc.edison.data.model.ArtLetterPreviewEntity
import com.umc.edison.data.model.IdentityEntity
import com.umc.edison.data.model.InterestEntity
import com.umc.edison.data.model.UserEntity

interface UserRemoteDataSource {
    suspend fun getAllMyIdentityResults(): List<IdentityEntity>
    suspend fun getMyInterestResult(categoryNumber: String): InterestEntity
    suspend fun getLogInState(): Boolean
    suspend fun getMyScrapArtLetters(): List<ArtLetterCategoryEntity>
    suspend fun getProfileInfo(): UserEntity
    suspend fun getScrapArtLettersByCategory(category: String): List<ArtLetterPreviewEntity>

    suspend fun updateProfileInfo(user: UserEntity)
    suspend fun updateIdentity(identity: IdentityEntity)
    suspend fun updateInterest(interest: InterestEntity)

    suspend fun getMyIdentityResult(categoryNumber: String): IdentityEntity

    suspend fun logOut()
    suspend fun deleteAccount()


    suspend fun googleLogin(idToken: String): UserEntity
    suspend fun makeNickName(nickname: String)
    suspend fun getInterestKeywordsByCategory(categoryNumber: String): InterestEntity
    suspend fun getIdentityKeywordsByCategory(categoryNumber: String): IdentityEntity
    suspend fun setUserIdentity(identity: IdentityEntity)
    suspend fun setUserInterest(interest: InterestEntity)
}