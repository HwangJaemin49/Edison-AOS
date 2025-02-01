package com.umc.edison.domain.repository

import com.umc.edison.domain.DataResource
import com.umc.edison.domain.model.ArtLetter
import com.umc.edison.domain.model.ArtLetterCategory
import com.umc.edison.domain.model.Identity
import com.umc.edison.domain.model.IdentityCategory
import com.umc.edison.domain.model.Interest
import com.umc.edison.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getAllMyIdentityResults(): Flow<DataResource<List<Identity>>>
    fun getMyInterestKeyword(): Flow<DataResource<Interest>>
    fun getLogInState(): Flow<DataResource<Boolean>>
    fun getMyScrapArtLetters(): Flow<DataResource<List<ArtLetterCategory>>>
    fun getProfileInfo(): Flow<DataResource<User>>
    fun getScrapArtLettersByCategory(categoryId: Int): Flow<DataResource<List<ArtLetter>>>

    fun updateProfileInfo(user: User): Flow<DataResource<Unit>>
    fun updateIdentity(identity: Identity): Flow<DataResource<Unit>>

    fun getMyIdentityResult(identityCategory: IdentityCategory): Flow<DataResource<Identity>>

}