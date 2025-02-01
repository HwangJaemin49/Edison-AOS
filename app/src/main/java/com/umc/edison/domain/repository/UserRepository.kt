package com.umc.edison.domain.repository

import com.umc.edison.domain.DataResource
import com.umc.edison.domain.model.ArtLetter
import com.umc.edison.domain.model.ArtLetterCategory
import com.umc.edison.domain.model.Identity
import com.umc.edison.domain.model.IdentityCategory
import com.umc.edison.domain.model.Interest
import com.umc.edison.domain.model.InterestCategory
import com.umc.edison.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getLogInState(): Flow<DataResource<Boolean>>

    fun getProfileInfo(): Flow<DataResource<User>>
    fun getAllMyIdentityResults(): Flow<DataResource<List<Identity>>>
    fun getMyInterestResult(interestCategory: InterestCategory): Flow<DataResource<Interest>>
    fun getMyScrapArtLetters(): Flow<DataResource<List<ArtLetterCategory>>>
    fun getScrapArtLettersByCategory(categoryId: Int): Flow<DataResource<List<ArtLetter>>>

    fun getMyIdentityResult(identityCategory: IdentityCategory): Flow<DataResource<Identity>>

    fun updateProfileInfo(user: User): Flow<DataResource<Unit>>
    fun updateIdentity(identity: Identity): Flow<DataResource<Unit>>
    fun updateInterest(interest: Interest): Flow<DataResource<Unit>>
}