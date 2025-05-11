package com.umc.edison.data.datasources

import com.umc.edison.data.model.identity.IdentityEntity
import com.umc.edison.data.model.user.UserEntity

interface UserRemoteDataSource {
    // CREATE
    suspend fun addIdentity(identity: IdentityEntity)
    suspend fun googleLogin(idToken: String): UserEntity

    // READ
    suspend fun getAllMyIdentityResults(): List<IdentityEntity>
    suspend fun getAllRecentSearches() : List<String>
    suspend fun getIdentityByCategory(categoryNumber: String): IdentityEntity
    suspend fun getLogInState(): Boolean
    suspend fun getMyIdentityResult(categoryNumber: String): IdentityEntity
    suspend fun getMyProfileInfo(): UserEntity

    // UPDATE
    suspend fun logOut()
    suspend fun updateIdentity(identity: IdentityEntity)
    suspend fun updateProfileInfo(user: UserEntity)

    // DELETE
    suspend fun deleteRecentSearch(search: String)
    suspend fun deleteUser()
}