package com.umc.edison.data.datasources

import com.umc.edison.data.model.identity.IdentityEntity
import com.umc.edison.data.model.user.UserEntity
import com.umc.edison.domain.model.identity.IdentityCategory

interface UserRemoteDataSource {
    // CREATE
    suspend fun addIdentity(identity: IdentityEntity)
    suspend fun googleLogin(idToken: String): UserEntity

    // READ
    suspend fun getAllMyIdentityResults(): List<IdentityEntity>
    suspend fun getAllRecentSearches() : List<String>
    suspend fun getIdentityByCategory(category: IdentityCategory): IdentityEntity
    suspend fun getLogInState(): Boolean
    suspend fun getMyIdentityResult(category: IdentityCategory): IdentityEntity
    suspend fun getMyProfileInfo(): UserEntity

    // UPDATE
    suspend fun logOut()
    suspend fun updateIdentity(identity: IdentityEntity)
    suspend fun updateProfileInfo(user: UserEntity)

    // DELETE
    suspend fun deleteRecentSearch(search: String)
    suspend fun deleteUser()
}