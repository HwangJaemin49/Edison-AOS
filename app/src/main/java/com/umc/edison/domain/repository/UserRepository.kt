package com.umc.edison.domain.repository

import com.umc.edison.domain.DataResource
import com.umc.edison.domain.model.ArtLetter
import com.umc.edison.domain.model.ArtLetterCategory
import com.umc.edison.domain.model.IdentityKeyword
import com.umc.edison.domain.model.InterestKeyword
import com.umc.edison.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getMyIdentityKeywords(): Flow<DataResource<List<IdentityKeyword>>>
    fun getMyInterestKeyword(): Flow<DataResource<InterestKeyword>>
    fun getLogInState(): Flow<DataResource<Boolean>>
    fun getMyScrapArtLetters(): Flow<DataResource<List<ArtLetterCategory>>>
    fun getProfileInfo(): Flow<DataResource<User>>
    fun getScrapArtLettersByCategory(categoryId: Int): Flow<DataResource<List<ArtLetter>>>

    fun updateProfileInfo(user: User): Flow<DataResource<Unit>>

}